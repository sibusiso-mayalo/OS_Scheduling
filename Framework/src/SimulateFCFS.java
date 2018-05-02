import java.util.Scanner;
public class SimulateFCFS
{
  private Scanner input;
  private String configFileName;

  private int syscall_cost;
  private int switch_cost;
  private int trace_level;

  public void simulateMenu() // prints out the menu and get user input
  {
    this.input = new Scanner(System.in);
    System.out.println("*** FCFS Simulator ***");

    System.out.print("enter configuration file name: ");
    this.configFileName = this.input.nextLine();

    System.out.print("\nEnter cost of system call: ");
    this.syscall_cost = this.input.nextInt();

    System.out.print("\nEnter cost of context switch: ");
    this.switch_cost = this.input.nextInt();

    System.out.print("\nEnter trace level: ");
    this.trace_level = this.input.nextInt();

  }

  public void resultMenu(int sysTime, int kernelTime, int userTime, int idleTime, int contextTime, int utilTime)
  {
    //print out the given results to the user

    System.out.println("*** Results ***");
    System.out.println("System time: " + sysTime);
    System.out.println("Kernel time: " + kernelTime);
    System.out.println("User time: " + userTime);
    System.out.println("Idle time: " + idleTime);
    System.out.println("Context switches: " + contextTime);
    System.out.println("CPU utilization: " + utilTime);

  }

  public String toString() // returns all the values given by the user in the simulateMenu function
  {
    return this.configFileName +" "+ this.syscall_cost +" "+ this.switch_cost +" "+ this.trace_level;
  }

  public static void main(String[] a)
  {

  }
}
