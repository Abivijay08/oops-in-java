import java.util.*;
abstract class shape{
int x,y;
abstract void printArea();
public shape(int x ,int y){
this.x=x; 
this.y=y;
}
}
class Rect extends shape{
Rect(int l,int b){
super(l,b);
}
void printArea(){
System.out.println("Area of rectangle:"+(x*y));
}
}
class Circle extends shape{
Circle(int r){
super(r,0);
}
void printArea(){
System.out.println("Area of Circle:"+(3.14*x*x));
}
}
class Tri extends shape{
Tri(int ba,int h){
super(ba,h);
}
void printArea(){
System.out.println("Area of Triangle:"+(0.5*x*y));
}
}
public class Area{
public static void main(String[] args){
while(true){
Scanner sc=new Scanner(System.in);
System.out.print("\n1.Rectangle\n2.Circle\n3.Triangle\n");
System.out.print("Enter choice to calculate area:");
int a=sc.nextInt();
if(a==1){
System.out.println("Enter length and breadth:");
int x=sc.nextInt();
int y=sc.nextInt();
shape rec=new Rect(x,y);
rec.printArea();
}else if(a==2){
System.out.println("Enter radius:");
int x=sc.nextInt();
shape cir=new Circle(x);
cir.printArea();
}else{
System.out.println("Enter base and height:");
int x=sc.nextInt();
int y=sc.nextInt();
shape tri=new Tri(x,y);
tri.printArea();
}
}
}
}

