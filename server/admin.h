/* Funzione Admin */

void admin(void *arg)
{
	int connfd=*((int*)arg);
	int fd;
	printf("Sei un admin\n");


	char msg[30];
	while((n=recv(connfd,msg,MAXSZ,MSG_NOSIGNAL)) > 0)
	{
		/* Il server invia i risultati al client */
	  	if(strncmp(msg,"risultati",9)==0) 
	  	{
	  		char buff[1024];
	  		fd = open("partite_giocate.log",O_RDONLY,S_IRUSR | S_IWUSR | S_IXUSR);
	  		lseek(fd,0,SEEK_SET);
	  		int len = read(fd,buff,1024);
	  		//int len = strlen(buff);
	  		int c = 0;
	  		for(i=0;i<len;i++)
	  		{
	  			if(buff[i]=='\n')
	  			{
	  				c++;
	  			}
	  		}

	  		char count[5];
	  		sprintf(count,"%d\n",c);
	  		fullSend(connfd,count,strlen(count),MSG_NOSIGNAL); // Invio il numero delle partite giocate
	  		fullSend(connfd,buff,strlen(buff),MSG_NOSIGNAL); // Invio il buffer contenente le partite giocate

	  	}
	  	/* Il server invia le partite in corso al client */ 
	  	else if(strncmp(msg,"partite",7)==0)
	  	{

	  		fullSend(connfd,"partite\n",8,MSG_NOSIGNAL);

	  		char num[4];
	  		memset(num,' ',4);

	  		sprintf(num,"%d\n",partiteCount);

	  		fflush(stdin);

	  		fullSend(connfd,num,strlen(num),MSG_NOSIGNAL);


	  		creaPartiteInCorso(partite);

	  		send(connfd,buffPartite,strlen(buffPartite),MSG_NOSIGNAL);
	  	}

	  	memset(msg,0,sizeof(msg));
	  	fflush(stdin);
	  	fflush(stdout);
	}

	close(connfd);



	printf("Connessione Client chiusa\n");
	pthread_exit(0);

}
