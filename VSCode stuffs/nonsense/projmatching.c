#include <stdio.h>
#include <stdlib.h>

typedef struct _listnode
{
 int vertex;
 struct _listnode *next;
} ListNode;
typedef ListNode StackNode;

typedef struct _graph{
 int V;
 int E;
 ListNode **list;
}Graph;

typedef ListNode QueueNode;

typedef struct _queue{
 int size;
 QueueNode *head;
 QueueNode *tail;
} Queue;

typedef struct _stack
{
 int size;
 StackNode *head;
} Stack;

void insertAdjVertex(ListNode** AdjList,int vertex);
void removeAdjVertex(ListNode** AdjList,int vertex);
int matching(Graph g);

void enqueue(Queue *qPtr, int item);
int dequeue(Queue *qPtr);
int getFront(Queue q);
int isEmptyQueue(Queue q);
void removeAllItemsFromQueue(Queue *qPtr);
void printQ(QueueNode *cur);
//////STACK///////////////////////////////////////////
void push(Stack *sPtr, int vertex);
int pop(Stack *sPtr);
int peek(Stack s);
int isEmptyStack(Stack s);
void removeAllItemsFromStack(Stack *sPtr);
//////////////////////////////////

int main()
{
 int Prj, Std, Mtr; //Project, Student and Mentor;
 int maxMatch;
 scanf("%d %d %d", &Std, &Prj, &Mtr);

 int np,nm; //number of projects and number of mentors

 //build graph
 Graph g;

	int i,j,k;
	int StdInput[Prj+Mtr+2];
	int endIndex = Std*2+Prj+Mtr+2;

	// Graph vertex is in sequence, by the order of
	// source, Std set1, Prj, Mtr, Std set2, mid, end

	g.V = Std*2+Prj+Mtr+3;
    g.list = (ListNode **) malloc(g.V*sizeof(ListNode *));

    for(i=0;i<g.V;i++)
        g.list[i] = NULL;

    // Connect start point and project
    for( i=0; i<Prj; i++){
        insertAdjVertex(&g.list[0], i+Std+1);
        g.E++;
    }
    // Connect mentor and end point
    for( i=0; i<Mtr; i++){
        insertAdjVertex(&g.list[i+Std+Prj+1], endIndex);
        g.E++;
    }
    // Connect both student subsets to mid points
    for (i=0; i<Std; i++){
        insertAdjVertex(&g.list[i+1], endIndex-1);
        g.E++;
        insertAdjVertex(&g.list[endIndex-1], i+Std+Prj+Mtr+1);
        g.E++;
    }

    //printGraphList(g);

    for(i=0; i<Std; i++){
        //printf("Enter the student's preferred projects and mentors:\n");
        // Get user input and store in an array

        scanf("%i", &StdInput[0]);
        scanf("%i", &StdInput[1]);



        // Get preferred projects, directed from prj to std set1, prj = +Std
        for (j=0; j<StdInput[0]; j++){
            scanf("%i", &StdInput[j+2]);
            if (StdInput[0] != 0 && StdInput[1] != 0){
                insertAdjVertex(&g.list[StdInput[j+2]+Std], i+1);
                g.E++;
            }

        }
        // Get preferred mentors, directed from std to mtr, mtr = +Std+Prj-1
        for( k=0; k<StdInput[1]; k++){
            scanf("%i", &StdInput[k+StdInput[0]+2]);
            if (StdInput[0] != 0 && StdInput[1] != 0){
                insertAdjVertex(&g.list[i+Std+Prj+Mtr+1], StdInput[k+StdInput[0]+2]+Std+Prj);
                g.E++;
            }
        }
    }
    //printGraphList(g);

    //apply Ford Fulkerson algorithm
    // use DFS or BFS to find a path

	maxMatch = matching(g);

    printf("%d\n", maxMatch);

    return 0;
}

int BFS(Graph *g){

    int visited[g->V];
    memset(visited, 0, sizeof(visited));

    int parent[g->V];
    memset(parent, -1, sizeof(parent));

    int parentV, endV;
    //printf("visited[g->V] = %d.\n", visited[g->V-1]);

    Queue q;
    q.head=NULL;
    q.tail=NULL;
    q.size=0;

    ListNode *tmp;

    enqueue(&q, 0);
    visited[0] = 1;

    while (isEmptyQueue(q) == 0){

        //printf("1. first while loop.\n");
        if ( getFront(q) == g->V-1 ){

            // revert path here //
            endV = g->V - 1;
            while (parent[endV] != -1){
                // revert endV & parent[endV]
                insertAdjVertex(&(g->list[endV]), parent[endV]);
                removeAdjVertex(&(g->list[parent[endV]]), endV);

                endV = parent[endV];
            }
            return 1;
        }
        else{
            parentV = getFront(q);
        }


        tmp = g->list[parentV];

        while (tmp!=NULL){
            //printf("3. While tmp!= NULL.\n");

            if (visited[tmp->vertex] == 0){
                //printf("4. if node not visited.\n");
                parent[tmp->vertex] = parentV;
                enqueue(&q, tmp->vertex);
                visited[tmp->vertex] = 1;
            }
            tmp = tmp->next;
        }
        dequeue(&q);
    }
    //printf("4. End of the big while loop.\n");
    return 0;
}

 //apply Ford Fulkerson algorithm
 // use DFS or BFS to find a path
 //maxMatch = matching(g);
 //printf("%d\n", maxMatch);
 //return 0;


int matching(Graph g)
{

    //Write your code
    //printf("matching.\n");
    int match = 0;

    while (BFS(&g) == 1){
        //printGraphList(g);
        match++;
        //printf("1. match = %d.\n", match);
    }
    //printf("2. match = %d.\n", match);
    return match;
}

void removeAdjVertex(ListNode** AdjList,int vertex)
{
 ListNode *temp, *preTemp;
 if(*AdjList != NULL)
 {
    if((*AdjList)->vertex ==vertex){//first node
      temp = *AdjList;
      *AdjList = (*AdjList)->next;
      free(temp);
      return;
    }
    preTemp = *AdjList;
    temp = (*AdjList)->next;
    while(temp!=NULL && temp->vertex != vertex)
    {
      preTemp= temp;
      temp = temp->next;
    }
    preTemp->next = temp->next;
    free(temp);
   }
}

void insertAdjVertex(ListNode** AdjList,int vertex)
{
  ListNode *temp;
  if(*AdjList==NULL)
  {
     *AdjList = (ListNode *)malloc(sizeof(ListNode));
     (*AdjList)->vertex = vertex;
     (*AdjList)->next = NULL;
  }
  else{
     temp = (ListNode *)malloc(sizeof(ListNode));
     temp->vertex = vertex;
     temp->next = *AdjList;
     *AdjList = temp;
  }
}

void enqueue(Queue *qPtr, int vertex) {
  QueueNode *newNode;
  newNode = malloc(sizeof(QueueNode));
  if(newNode==NULL) exit(0);

  newNode->vertex = vertex;
  newNode->next = NULL;

  if(isEmptyQueue(*qPtr))
     qPtr->head=newNode;
  else
     qPtr->tail->next = newNode;

     qPtr->tail = newNode;
     qPtr->size++;
}

int dequeue(Queue *qPtr) {
   if(qPtr==NULL || qPtr->head==NULL){ //Queue is empty or NULL pointer
     return 0;
   }
   else{
     QueueNode *temp = qPtr->head;
     qPtr->head = qPtr->head->next;
     if(qPtr->head == NULL) //Queue is emptied
       qPtr->tail = NULL;

     free(temp);
     qPtr->size--;
     return 1;
}
}

int getFront(Queue q){
    return q.head->vertex;
}

int isEmptyQueue(Queue q) {
   if(q.size==0) return 1;
   else return 0;
}

void removeAllItemsFromQueue(Queue *qPtr)
{
  while(dequeue(qPtr));
}

void printQ(QueueNode *cur){
 if(cur==NULL) printf("Empty");

 while (cur != NULL){
    printf("%d ", cur->vertex);
    cur = cur->next;
  }
 printf("\n");
}

void push(Stack *sPtr, int vertex)
{
  StackNode *newNode;
  newNode= malloc(sizeof(StackNode));
  newNode->vertex = vertex;
  newNode->next = sPtr->head;
  sPtr->head = newNode;
  sPtr->size++;
}

int pop(Stack *sPtr)
{
  if(sPtr==NULL || sPtr->head==NULL){
     return 0;
  }
  else{
     StackNode *temp = sPtr->head;
     sPtr->head = sPtr->head->next;
     free(temp);
     sPtr->size--;
     return 1;
   }
}

int isEmptyStack(Stack s)
{
    if(s.size==0) return 1;
    else return 0;
}

int peek(Stack s){
   return s.head->vertex;
}

void removeAllItemsFromStack(Stack *sPtr)
{
   while(pop(sPtr));
}