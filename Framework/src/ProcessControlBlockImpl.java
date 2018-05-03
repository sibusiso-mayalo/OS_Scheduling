import simulator.Instruction;
import java.util.Random;
import java.util.ArrayDeque;
import simulator.ProcessControlBlock;
import java.io.File;
import java.util.Scanner;
import simulator.CPUInstruction;
import simulator.IOInstruction;
import java.io.FileNotFoundException;
abstract class ProcessControlBlockImpl implements simulator.ProcessControlBlock
{
  private int priority;
  private int pid;
  private String programName;
  private State currentState;
  private Instruction currentInstruction;
  private ArrayDeque<Instruction> instruction;
  static Random random;

  private int generate_PID(){return random.nextInt();}
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

  public void nextInstruction(){
    this.currentInstruction = this.instruction.pop();
    this.currentInstruction = this.instruction.getFirst();
  }

  public ProcessControlBlockImpl(String filename)
  {
    this.programName = filename;
    this.pid = generate_PID();
    //sthis.priority = generate_priority();
    this.currentState = ProcessControlBlock.State.READY;
    this.instruction = new ArrayDeque();
    this.currentInstruction = null;
  }

  public ProcessControlBlock loadProgram(String filename)
  {
    ProcessControlBlock parentBlock = this;
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
    this.currentInstruction = instruction.getFirst();
    return parentBlock;
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
    return this.pid +" "+ this.currentState +" "+ this.programName;
  }
}
