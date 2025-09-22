import java.util.*;

class bill{
public static void main(String args[]){
double a1=0;

Scanner n1=new Scanner(System.in);
System.out.println("Enter the consumer name:");
String name=n1.nextLine();
Scanner n2=new Scanner(System.in);
System.out.println("Enter the consumer number:");
int number=n2.nextInt();
Scanner n3=new Scanner(System.in);
System.out.println("Enter the consumer type:");
String type=n3.nextLine();

System.out.println("enter current reading");
int b1=n1.nextInt();
System.out.println("enter previous reading");
int b2=n1.nextInt();
int a=b1-b2;
System.out.println("enter consumed unit"+a);

Scanner n4=new Scanner(System.in);
System.out.println("1,domestic\n2.commercial\nenter(1/2):");
int choice=n4.nextInt();

if(choice==1){
if(a<100){
a1=a*1;
}else if(a>100 && a<=200){
a1=a*2.50;
}else if(a>200 && a<=300){
a1=a*4;
}else{
a1=a*6;
}
System.out.println("Domestic account:"+a1);
}


else if(choice==2){
if(a<100){
a1=a*2;
}else if(a>100 && a<=200){
a1=a*4.50;
}else if(a>200 && a<=300){
a1=a*6;
}else{
a1=a*7;
}
System.out.println("Commercial account:"+a1);
}

else{
System.out.println("initial value:");
}
}
}

