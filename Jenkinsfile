pipeline{
    agent any
    tools{
        maven 'maven-3.9.8'
    }
    environment {
        docker_registry = 'iamroyalreddy/fusion-be'
        DOCKERHUB_CREDENTIALS = credentials('docker-credentials')
    }
    options {
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
    }
    stages{
        stage('Build and Package'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }
        // stage ("code quality") {
        //     steps {
        //         script {
        //             withSonarQubeEnv(installationName: 'sonarqube', credentialsId: 'sonar-credentials') {
        //             sh '''
        //                 mvn clean verify sonar:sonar -DskipTests \ // need to work on test cases
        //                 -Dsonar.projectKey=fusion-backend \ 
        //                 -Dsonar.projectName='fusion-backend' \
        //                 -Dsonar.host.url=http://54.242.152.54:9000 \
        //                 -Dsonar.token=sqp_c25ea4885d079ea10d1e95d2b246e033a5f371bb
        //             '''
        //             }
        //         }
        //     }
        // }
        stage('containerization') {
            steps {
                sh '''
                    docker rm -f `docker ps -a`
                    docker rmi `docker images`
                    docker build -t $docker_registry:$GIT_COMMIT .
                '''
            }
        }
        stage('Publish Docker Image') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh "docker push $docker_registry:$GIT_COMMIT"
            }       
        }

    }

    post { 
    always { 
        echo "\033[34mJob completed. Cleaning up workspace...\033[0m"
        deleteDir()
    }
    success {
        echo "\033[33mPipeline completed successfully. Performing success actions...\033[0m"
        // Add additional actions here if needed, like sending success notifications
    }
    failure { 
        echo "\033[35mPipeline failed. Triggering failure response...\033[0m"
        // send notification
    }
    unstable {
        echo "\033[34mPipeline marked as unstable. Reviewing issues...\033[0m"
        // Send notification or take action for unstable builds, if needed
    }
    aborted {
        echo "\033[33mPipeline was aborted. Clearing any partial artifacts...\033[0m"
        // Any specific actions for aborted jobs
    }
}
}