node {
    stage('Checkout') { 
        echo 'code checkout stage....'
        git 'https://github.com/rchristopher9913/medicure-app-assignment-project.git'
        
    }
    stage('Compile') {
        echo 'code compile stage....'
        sh 'mvn clean package'
    }
    stage('Containarize') {
        sh 'docker build -t rchristopher9913/medicure-app:3.0 .'
    }
    stage('Release') {
        withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
    // some block
        sh "docker login -u rchristopher9913 -p ${dockerHubPwd}"
        sh 'docker push rchristopher9913/medicure-app:3.0'
        }
        
    }
}
