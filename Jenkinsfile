pipeline {
    agent {
        docker { 
            image 'gradle:4.8.1-jdk8-slim'
        }
    }

    stages {

        stage("Build") {
            steps {
                echo "Build"
                sh 'gradle clean install'
            }
        }

    }

    post {
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