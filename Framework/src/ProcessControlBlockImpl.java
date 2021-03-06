import simulator.Instruction;
import java.util.Random;
import java.util.ArrayDeque;
import simulator.ProcessControlBlock;
import java.io.File;
import java.util.Scanner;
import simulator.CPUInstruction;
import simulator.IOInstruction;
import java.io.FileNotFoundException;
class ProcessControlBlockImpl implements simulator.ProcessControlBlock
{
  private static int priority;
  private static int pid;
  private static String programName;
  private static State currentState;
  private static Instruction currentInstruction;
  private static ArrayDeque<Instruction> instruction;
  private static Random random;
  private ProcessControlBlockImpl pcbImpl;

  public ProcessControlBlockImpl()
  {

  }

  private static int generate_PID(){return random.nextInt();}
  private int generate_priority(){
    int i = -1;
    while(!(i>-1 && i<5))
    {
      i = random.nextInt();
    }
    return i;
  }

  public String getProgramName(){return programName;}
  public int getPID(){return pid;}

  public int getPriority(){return priority;}
  public State getState(){return this.currentState;}

  public void setState(State state){this.currentState = state;}
  public int setPriority(int new_priority)
  {
    int previousPriority = priority;
    priority = new_priority;
    return previousPriority;
  }

  public void nextInstruction(){
    this.currentInstruction = this.instruction.pop();
    this.currentInstruction = this.instruction.getFirst();
  }

  /* public ProcessControlBlockImpl(String filename)
  {
    this.programName = filename;
    this.pid = generate_PID();
    //sthis.priority = generate_priority();
    this.currentState = ProcessControlBlock.State.READY;
    this.instruction = new ArrayDeque();
    this.currentInstruction = null;
  }*/

  public ProcessControlBlock loadProgram(String filename)
  {
    /* Initialization of variables*/
    programName = filename;
    pid = generate_PID();
    //sthis.priority = generate_priority();
    currentState = ProcessControlBlock.State.READY;
    instruction = new ArrayDeque();

    /* Implementation of loadProgram*/

    File openFile;
    try
    {
      openFile = new File(filename);
      Scanner readFile = new Scanner(openFile);

      while(readFile.hasNext())
      {
        String line_on_file = readFile.nextLine();
        if(!(line_on_file.charAt(0) == '#'))
        {
          //This is not a comment
          String type = line_on_file.split(" ")[0];

          switch(type)
          {
            case "CPU":
            {
              CPUInstruction new_instruction = new CPUInstruction(Integer.parseInt(line_on_file.split(" ")[1]));
              instruction.addFirst(new_instruction);
              break;
            }
            case "IO":
            {
              IOInstruction new_instruction = new IOInstruction(Integer.parseInt(line_on_file.split(" ")[1]), Integer.parseInt(line_on_file.split(" ")[2]));
              instruction.addFirst(new_instruction);
              break;
            }
          }
        }
      }
    //  openFile.close();
    }catch(FileNotFoundException exception)
    {
      System.out.println("Could not open file:" + exception.getMessage());
    }
    currentInstruction = instruction.getFirst();
    return this;
  }

  public boolean hasNextInstruction()
  {
    boolean they_are_there = false;
    if(instruction.size() != 0){ they_are_there = true;}
    return they_are_there;
  }

  public Instruction getInstruction(){return instruction.getFirst();}

  public String toString()
  {
    return pid +" "+ currentState +" "+ programName;
  }
}
