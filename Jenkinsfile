pipeline {
    agent {
        docker { 
            image "gradle:4.8.1-jre8"
        }
    }

    stages {

        stage("Prepare") {
            steps {
                sh "gradle clean check"
            }
        }

        stage("Build") {
            steps {
                sh "gradle clean check"
            }
        }

        stage("Test") {
            steps {
                sh "gradle test jacocoTestReport"
            }
        }

        stage("Analysis") {
            steps {
                sh "set +x"
                sh "gradle sonarqube -Dsonar.organization=zero-88-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_TOKEN}"
            }
        }

    }

    post {
        always {
            junit 'build/test-results/**/*.xml'
            archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
            zip archive: true, dir: "build/libs", glob: "**/*.jar", zipFile: "dist/artifact.zip"
            zip archive: true, dir: "build/reports", zipFile: "dist/test-reports.zip"
        }
        failure {
            script {
                def committerEmail = sh (script: 'git --no-pager show -s --format=\'%ae\'', returnStdout: true).trim()
                def committer = sh (script: 'git --no-pager show -s --format=\'%an\'', returnStdout: true).trim()
                def content = """
                    - Job Name: ${env.JOB_NAME}
                    - Build URL: ${env.BUILD_URL}
                    - Changes:
                        - ${committer} <${committerEmail}>
                        - ${env.GIT_COMMIT}
                        - ${env.GIT_BRANCH}
                        - ${env.GIT_URL}
                """
                emailext (
                    recipientProviders: [[$class: "DevelopersRecipientProvider"]],
                    subject: "[Jenkins] ${env.JOB_NAME}-#${env.BUILD_NUMBER} [${currentBuild.result}]",
                    body: "${content}",
                    attachLog: true,
                    compressLog: true
                )
            }
        }
    }
}