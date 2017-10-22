#include "header.h"
#include "lista.h"
#include "admin.h"
#include "utente.h"

/* Funzione del thread */

void *gestisci(void *arg)
{
	// Connessione ricavata dall'argomento del thread
	int connfd=*((int*)arg);

	char tipoUtente[30];
	recv(connfd,tipoUtente,30,0);

	if(strncmp(tipoUtente,"admin",5)==0)
	{
		admin(&connfd);
	}
	else
	{
		utente(&connfd);
	}

}



/* INIZIO MAIN */


int main()
{

 int newsockfd;//to accept connection
 int sockfd;//to create socket


 struct sockaddr_in serverAddress;//server receive on this address
 struct sockaddr_in clientAddress;//server sends to client on this address



 int clientAddressLength;
 int pid;


 //creazione socket
 sockfd=socket(AF_INET,SOCK_STREAM,0);
 //inizializzazione socket
 memset(&serverAddress,0,sizeof(serverAddress));
 serverAddress.sin_family=AF_INET;
 serverAddress.sin_addr.s_addr=htonl(INADDR_ANY);
 serverAddress.sin_port=htons(PORT);



/* Codice che evita il fastidioso messaggio di errore "Address already in use" */
	int yes=1;
	if (setsockopt(sockfd,SOL_SOCKET,SO_REUSEADDR,&yes,sizeof(int))== -1) {
	perror("setsockopt");
	exit(1);
}



 //bind the socket with the server address and port
 bind(sockfd,(struct sockaddr *)&serverAddress, sizeof(serverAddress));

 // In attesa di connesioni
 listen(sockfd,5);

 while(1)
 {
  //il processo padre attende per una nuova connessione
  printf("\n*****Server in attesa di connessioni:*****\n");
  clientAddressLength=sizeof(clientAddress);
  if((newsockfd=accept(sockfd,(struct sockaddr*)&clientAddress,&clientAddressLength)) < 0)
			perror("accept errore :");

  printf("Connesso al client: %s\n",inet_ntoa(clientAddress.sin_addr));

	// Viene creato un thread per ogni nuova connessione
  pthread_t tr;
  if ( pthread_create(&tr,NULL,gestisci,&newsockfd) != 0 )
            perror("Errore creazione thread");


 }//chiusura while esterno

 return 0;
}
