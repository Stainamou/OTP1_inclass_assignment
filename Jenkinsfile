pipeline {
  agent any

  tools {
    // these names must match tools configured in Jenkins Global Tool Configuration
    jdk 'jdk11'
    maven 'maven3'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B clean test package'
          } else {
            bat 'mvn -B clean test package'
          }
        }
      }
      post {
        always {
          // publish JUnit results produced by Maven Surefire / Failsafe
          junit '**/target/surefire-reports/*.xml'
          // archive produced artifacts (jar)
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }
    }

    stage('Deploy (optional)') {
      when { branch 'master' }
      steps {
        // if you want automated deploy, configure credentials in Jenkins and set credentialsId
        withCredentials([usernamePassword(credentialsId: 'maven-deploy-creds', usernameVariable: 'MVN_USER', passwordVariable: 'MVN_PASS')]) {
          script {
            if (isUnix()) {
              sh 'mvn -B deploy -DskipTests -Dusername=$MVN_USER -Dpassword=$MVN_PASS'
            } else {
              bat 'mvn -B deploy -DskipTests -Dusername=%MVN_USER% -Dpassword=%MVN_PASS%'
            }
          }
        }
      }
    }

    stage('Build Docker image') {
      steps {
        script {
          def repo = 'stainamou/otp1-inclass' // update to your Docker Hub repo
          def imageTag = "${repo}:${env.BUILD_NUMBER}"
          if (isUnix()) {
            app = docker.build(imageTag)
          } else {
            // on Windows agent build with bat then reference the image
            bat "docker build -t ${imageTag} -f Dockerfile ."
            app = docker.image(imageTag)
          }
          env.IMAGE = imageTag
        }
      }
    }

    stage('Push to Docker Hub') {
      when { branch 'master' }
      steps {
        script {
          // requires a Jenkins credential of type "Username with password" id'd as docker-hub-creds
          docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-creds') {
            def img = docker.image(env.IMAGE)
            img.push()
            img.push('latest')
          }
        }
      }
    }
  }

  post {
    failure {
      echo "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }
  }
}
