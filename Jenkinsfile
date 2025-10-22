pipeline {
  agent any

  tools {
    // these names must match tools configured in Jenkins Global Tool Configuration
    jdk 'jdk23'
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
  }

  post {
    failure {
      echo "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }
  }
}
