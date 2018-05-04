import java.util.Scanner;
import simulator.ProcessControlBlock;
import simulator.Config;

public class SimulateFCFS
{
  private Scanner input;
  private String configFileName;
  private FCFSKernel kernel;

  private int syscall_cost;
  private int switch_cost;
  private int trace_level;

  public SimulateFCFS()
  {
    kernel = new FCFSKernel();
    simulateMenu(); // print menu

    Config.init(kernel, this.switch_cost, this.syscall_cost) ;
    Config.buildConfiguration(this.configFileName);
    Config.run();
    System.out.println(resultMenu());
    System.out.println("Context switches : " + Config.getCPU().getContextSwitches());
    System.out.println("CPU utilization : " + (double)Config.getSystemTimer().getUserTime()/Config.getSystemTimer().getSystemTime()*100);

  }

  private void simulateMenu() // prints out the menu and get user input
  {
    this.input = new Scanner(System.in);
    System.out.println("*** FCFS Simulator ***");

    System.out.print("Enter configuration file name: ");
    this.configFileName = this.input.nextLine();

    System.out.print("Enter cost of system call: ");
    this.syscall_cost = this.input.nextInt();

    System.out.print("Enter cost of context switch: ");
    this.switch_cost = this.input.nextInt();

    System.out.print("Enter trace level: ");
    this.trace_level = this.input.nextInt();

  }

  public String resultMenu()
  {
    //print out the given results to the user
    return Config.getSystemTimer().toString();
  }

  public String toString() // returns all the values given by the user in the simulateMenu function
  {
    return this.configFileName +" "+ this.syscall_cost +" "+ this.switch_cost +" "+ this.trace_level;
  }

  public static void main(String[] a)
  {
    try
    {
      SimulateFCFS simulationObject = new SimulateFCFS();

    }catch(Exception e)
    {
      System.out.println("Simulation error: "+ e.getMessage());
    }

  }
}
