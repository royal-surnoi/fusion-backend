pipeline{
    agent any
    tools{
        maven 'maven-3.9.8'
    }
    options {
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
    }
    stages{
        stage('Build and Package'){
            steps{
                sh 'mvn clean install -DskipTests'
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