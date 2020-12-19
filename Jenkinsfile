properties([pipelineTriggers([githubPush()])])

pipeline {
    environment { 
        registry = "mrpipikado/gt_back" 
        registryCredential = 'docker' 
        dockerImage = '' 
    }
   agent {
     node {
        label 'slave'
     }
   }

   stages {  
      stage('Clone'){
          steps{  
              git branch: 'dev',
                credentialsId: 'GitHub2',
                url: 'git@github.com:Yuvchenko-Kostiantyn/GoalTrackerBE.git'
          }
      }
      stage('build docker image') {
          steps {
          script { 
                    dockerImage = docker.build() registry + ":latest --network host" 
                }
          }
      }
      stage('Deploy image') { 
            steps { 
                script { 
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push() 
                    }
                } 
            }
      }
      stage ("triger deploy job") {		
            steps {
                build 'deploy on stage'	
            }
        } 
   }
}
