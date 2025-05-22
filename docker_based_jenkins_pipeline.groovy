pipeline {
  agent {label 'master'}
  stages {
    stage (cleanws) {
      steps {
         cleanws()
      }
    }
    stage ('check for the update') {
      steps {
         sshagent(['keypair']){
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo yum update'
         }
      }
    }
    stage ('install the docker package') {
      steps {
         sshagent(['keypair']){
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo yum install docker -y'
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo systemctl start docker'
         }
      }
           }
 stage ('docker pull basic images') {
      steps {
         sshagent(['keypair']){
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo docker pull ubuntu'
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo docker pull nginx'
         }
      }
  }
  stage ('docker images') {
      steps {
         sshagent(['keypair']){
            sh 'ssh -o StrictHostKeyChecking=no ${hostname} sudo docker images'
         }
      }
 }
}
}