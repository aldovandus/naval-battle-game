/* Procedura Utente */

void utente(void *arg)
{
	Utente *utente;
	Partita *partita = NULL;
	// Connessione ricavata dall'argomento del thread
	int connfd=*((int*)arg);

  	int mioid; // id del thread
	int stato = 0;
	int indice = pcont;

	/* Ottieni Indirizzo ip Client */
	char ip[INET_ADDRSTRLEN];
	struct sockaddr_in peeraddr;
	socklen_t peeraddrlen = sizeof(peeraddr);
	getpeername(connfd, (struct sockaddr*)&peeraddr, &peeraddrlen);
	inet_ntop(AF_INET, &(peeraddr.sin_addr), ip, INET_ADDRSTRLEN);


	/* Ricavo l'id dell'utente */
	char idText[5];
	printf("Inserisci il tuo id\n");
	fullRecv(connfd,idText,5,MSG_NOSIGNAL);

	/* Verifico se esiste già */
	if(verificaID(atoi(idText)) == NULL)
	{
		printf("si\n");
		fullSend(connfd,"Si\n",3,MSG_NOSIGNAL);
	}
	else
	{
		printf("no\n");
		fullSend(connfd,"No\n",3,MSG_NOSIGNAL);
	}
	printf("Id Accettato %s\n",idText);


  // Ogni thread prende il proprio id ed aggiorna il proprio stato
  pthread_mutex_lock(&mutex);
  mioid = atoi(idText);
  utente = malloc(sizeof(Utente));
  utente->stato = 0;
  utente->id=mioid;
  strcpy(utente->ip,ip);
  pcont++;
  aggiungi((void*)&utenti,utente);
  pthread_mutex_unlock(&mutex);



  //pthread_detach((int*)arg);

  /* Il thred si mette in attesa dei comandi da parte del client */

  while((n=recv(connfd,msg,MAXSZ,MSG_NOSIGNAL)) > 0)
  {

   /* Se il Cliente è stato accettato */
   if(strncmp(msg,"lista",5) == 0)
   {
   			char num[4];
			char id[4];

			/* Calcolo il numero di utenti connessi al server */
			memset(num,0,4);
			sprintf(num,"%d\n",pcont);

			fflush(stdin);
			int len = strlen(num);
			fullSend(connfd,num,len,MSG_NOSIGNAL);

			/* Crea la lista di utenti e la invia */
			char *buffLista = creaListaUtenti();
			len = sizeof(char)*50*pcont;
			
			fullSend(connfd,buffLista,len,MSG_NOSIGNAL);
			fflush(stdin);
			fflush(stdout);
			
   }
   else if(strncmp(msg,"invito",6) == 0)
   {
   		char buff[10];
		char id[4];
		int n;
   		char statoGame[5];
		Utente *u;


		//fullSend(connfd,"invito\n",7,MSG_NOSIGNAL);

		printf("Sto in attesa dell'id giocatore\n");
		n = fullRecv(connfd,id,4,MSG_NOSIGNAL);
		if(n==0)
		{
				perror("recv2 : ");
				break;
		}

			printf("Ricevuto %s \n",id);
	
		if(n > 0)
		{

			int i,esito = 0;
			char ip[16];

			Nodo* v = verificaID(atoi(id));
			if(v != NULL)
			{
				u = ((Utente*)v->dati);
				if(u->stato == 0)
				{
					esito = 1;
					sprintf(ip,"%s\n",u->ip);
					printf("Ip trovato : %s\n",ip);
				}
			}

		if(esito==1)
		{
				int len = strlen(ip);
				
				fullSend(connfd,ip,len,MSG_NOSIGNAL); // Invia l'ip del giocatore

				printf("Giocatore disponibile... %s creazione partita in corso ...\n",ip);

				char risp[3];

				printf("In attesa risposta...\n");
				fullRecv(connfd,risp,3,MSG_NOSIGNAL);


				if(strncmp(risp,"Si",2) == 0)
				{

				printf("Partita Creata...\n");

				/* Creazione partita */
				pthread_mutex_lock(&mutex);

				partita = malloc(sizeof(Partita));
				u->stato = 1;
				utente->stato = 1;
				//partita->id1=mioid;
				//partita->id2=atoi(id);
				partita->g1 = utente; // Assegnazione giocatore 1 alla partita
				partita->g2 = u; // Assegnazione giocatore 2 alla partita
				utente->partita = partita;
				u->partita = partita;
				aggiungi((void*)&partite,partita);
				partiteCount++;

				pthread_mutex_unlock(&mutex);

				/* Sto in game */
				fflush(stdin);
				
				}
				else{printf("Partita non Creata...\n");}


		}
		else
		{
				fullSend(connfd,"No\n",3,MSG_NOSIGNAL);
				printf("Giocatore non trovato oppure occupato\n");
		}

	}
		fflush(stdin);
		fflush(stdout);
   }
  
   else if(strncmp(msg,"fine",4) == 0)
   {

   		char numGioc[3];
   		fullRecv(connfd,numGioc,strlen(numGioc),MSG_NOSIGNAL); // Ottengo il numero(non l'id) del giocatore che vince/abbandona
   		printf("Numero giocatore %s\n", numGioc);

   		/* Se l'utente era in partita allora salva i risultati, resetta lo stato ed elimina la partita */

		pthread_mutex_lock(&mutex);
		if(utente->partita!=NULL)
		{
			printf("Dentro fine partita ...\n");
			salvaRisultati(connfd,utente->partita,atoi(numGioc));
    		utente->partita->g1->stato = 0;
			utente->partita->g2->stato = 0;
  			elimina((void*)&partite,utente->partita);
			utente->partita = NULL;
			partiteCount--;
		}
		pthread_mutex_unlock(&mutex);
		printf("Partita finita\n");
   }



   printf("Receive and set from pid %u thread %d : %s\n",(unsigned int)pthread_self(),mioid,msg);

   memset(msg,0,sizeof(msg));

   fflush(stdin);
   fflush(stdout);

	} // Fine del while principale

  
  close(connfd);

  pthread_mutex_lock(&mutex);
  if(partita != NULL) elimina((void*)&partite,partita);
  elimina((void*)&utenti,utente);
  pcont--;
  pthread_mutex_unlock(&mutex);

  printf("Connessione Client chiusa\n");


  pthread_exit(0);
}
