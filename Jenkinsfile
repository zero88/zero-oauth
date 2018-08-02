library identifier: 'notifications@master', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/zero-88/jenkins-pipeline-shared.git'])

pipeline {
    agent {
        docker "gradle:4.8.1-jdk8"
    }

    stages {

        stage("Build") {
            steps {
                sh "gradle clean assemble javadoc"
                script {
                    VERSION = sh(script: "gradle properties | grep 'version:' | awk '{print \$2}'", returnStdout: true).trim()
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: "build/libs/*.jar", fingerprint: true
                    archiveArtifacts artifacts: "build/distributions/*", fingerprint: true
                    zip archive: true, dir: "build/docs/javadoc", zipFile: "build/distributions/javadoc.zip"
                }
            }
        }

        stage("Test") {
            steps {
                sh "gradle test jacocoTestReport"
            }
            post {
                always {
                    junit 'build/test-results/**/*.xml'
                    zip archive: true, dir: "build/reports", zipFile: "build/distributions/test-reports.zip"
                }
            }
        }

        stage("Analysis") {
            steps {
                script {
                    withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                        sh "set +x"
                        sh "gradle sonarqube -Dsonar.organization=zero-88-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage("Publish") {
            when { tag "v*" }
            steps {
                // TODO: Update later
                echo "Deploy"
            }
        }

    }

    post {
        always {
            emailNotifications VERSION
        }
    }
}
