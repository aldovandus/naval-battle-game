

void aggiungi(void** lista,void *dati)
{
	Nodo *temp = malloc(sizeof(Nodo));
	temp->dati = dati;


	if(*lista == NULL)
	{
		*lista = temp;
		temp->next = NULL;
	}
	else
	{
		temp->next = *lista;
		*lista = temp;
	}
}


int elimina(Nodo **lista,void* num)
{
    Nodo *temp, *prev;
    temp=*lista;
    while(temp!=NULL)
    {
    if(temp->dati==num)
    {
        if(temp==*lista)
        {
        	*lista=temp->next;
        	free(temp);
        	return 1;
        }
        else
        {
        	prev->next=temp->next;
        	free(temp);
        	return 1;
        }
    }
    else
    {
        prev=temp;
        temp= temp->next;
    }
    }
    return 0;
}


void stampa(Nodo *lista)
{
	for(Nodo *temp = lista; temp != NULL; temp = temp->next)
	{
		//printf("Ecco : %d - %d\n", ((Partita*)temp->dati)->id1,((Partita*)temp->dati)->id2);
	}
}


char* creaListaUtenti()
{
	Nodo *temp;
	int i=0;
	char *temp2 = malloc(sizeof(char)*pcont*50);

	for(temp = (Nodo*)utenti;temp != NULL;temp = temp->next)
	{
			i += sprintf(temp2+i,"%d - %d - %s\n",((Utente*)temp->dati)->id,((Utente*)temp->dati)->stato,((Utente*)temp->dati)->ip);
	}

	return temp2;
}

void creaPartiteInCorso(Nodo* lista)
{

	Nodo *temp;
	int i=0;
	char *temp2 = malloc(sizeof(char)*partiteCount*30);

	for(temp = lista;temp != NULL;temp = temp->next)
	{
			i += sprintf(temp2+i,"%d - %d\n",((Partita*)lista->dati)->g1->id,((Partita*)lista->dati)->g2->id);
			printf("%d\n",i);
	}

	buffPartite = temp2;
	printf("%s - %d\n",buffPartite,(int)strlen(buffPartite));
}

Nodo* verificaID(int key)
{

	Nodo* trovato = NULL;

	for(Nodo *temp = (Nodo*)utenti; temp != NULL; temp = temp->next)
	{
		if(((Utente*)temp->dati)->id==key)
		{
			trovato = temp;
		}
	}

	return trovato;
}

void salvaRisultati(int connfd,Partita *partita,int esito)
{
		printf("Inizio Ricezione ...\n");
   		char str[80];
   		char idVincitore[30];

   		fflush(stdin);

   		printf("Fine Ricezione ...\n");

   		int len = sprintf(str, "%d-%d-%d\n", partita->g1->id,partita->g2->id, (esito==0) ? partita->g1->id : partita->g2->id);

   		//if(n>0)
   		{
   			int fd = open("partite_giocate.log",O_RDWR | O_CREAT | O_APPEND,S_IRUSR | S_IWUSR | S_IXUSR);
   			write(fd,str,len);
   		}
   		printf("Fine Scrittura ... \n"); printf("%s",str);

}


ssize_t fullSend(int fd, const void *buf, size_t count,int flags)
{
   size_t nleft;

    ssize_t nwritten;
      nleft = count;

    while (nleft > 0)
	{
     if ((nwritten = send(fd, buf, nleft,flags)) < 0){

     if (errno == EINTR){

       continue;

        }else{
       return(nwritten);
	}
    }  
     nleft -= nwritten;
    buf +=nwritten;

}
    return (nleft);
}

ssize_t fullRecv(int fd, void *buf, size_t count,int flags)
{
size_t nleft;
ssize_t nread;
nleft = count;
while (nleft > 0)
{
if ((nread = recv(fd, buf, nleft,flags)) > 0){

if (errno == EINTR){

continue;

}else{
return (nread);


}


}else if (nread == 0){

break;
}
nleft -= nread; 
buf += nread;


}
return (nleft);

}

