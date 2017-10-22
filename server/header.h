#include <stdio.h>
#include <arpa/inet.h>
#include <sys/types.h>//socket
#include <sys/socket.h>//socket
#include <string.h>//memset
#include <stdlib.h>//sizeof
#include <netinet/in.h>//INADDR_ANY
#include <syslog.h>
#include <netdb.h>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>
#include <fcntl.h>
#include <errno.h>



#define PORT 8025
#define MAXSZ 100


typedef struct utente Utente;
typedef struct nodo Nodo;
typedef struct partita Partita;

struct nodo
{
	void* dati;
	struct nodo *next;
};

struct partita
{
	struct utente *g1;
	struct utente *g2;
	struct partita *next;
};


struct utente
{
	int stato;
	int id;
	char ip[INET_ADDRSTRLEN];
	Partita *partita;
};



Nodo* partite = NULL; /* Lista Partite in corso */
Nodo* utenti; /* Lista Utenti in corso */

char *buffPartite;


char msg[MAXSZ];
int n;

int i=0;
int pcont=0;
int partiteCount = 0;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;