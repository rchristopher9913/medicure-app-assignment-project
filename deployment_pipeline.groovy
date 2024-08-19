node{
    stage('code checkout') {
        git 'https://github.com/rchristopher9913/medicure-app-assignment-project.git'
    }
    stage('deploy app to test server') {
        ansiblePlaybook become: true, credentialsId: 'ansible-creds', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: 'test-server-deploy.yaml', vaultTmpPath: ''
    }
    stage('selenium test checkout') {
        git 'https://github.com/rchristopher9913/insure-me-app-test.git'
        
    }
    stage('build selenium code') {
        sh 'mvn clean package assembly:single'
        
    }
    stage('Execute selenium tests') {
        sh 'java -jar target/insure-me-test-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar'
        
    }
    stage('Deploy app to prod server') {
        git 'https://github.com/rchristopher9913/medicure-app-assignment-project.git'
        ansiblePlaybook become: true, credentialsId: 'ansible-creds', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: 'prod-server-deploy.yaml', vaultTmpPath: ''
    }
}
