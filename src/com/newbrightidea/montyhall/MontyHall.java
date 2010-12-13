package com.newbrightidea.montyhall;

public class MontyHall {
  // TODO: expand this to include eg. switch_to_win, switch_from_win, etc.
  private enum ResultType { WIN, LOSS }
  public final int[] results = new int[ResultType.values().length];
  public final boolean[] doors;
  public int ixPrize;
  public int ixChosen;
  public int ixNewChosen;
  public int ixDoorToOpen;

  public MontyHall( int numDoors )
  {
    // initialize doors to false
    doors = new boolean[ numDoors ];
  }

  public void reset()
  {
    for ( int i = 0; i < doors.length; i++ )
    {
      doors[i] = false;
    }
    ixPrize = 0;
    ixChosen = 0;
    ixNewChosen = 0;
    ixDoorToOpen = 0;
  }
  
  public void placePrize()
  {
    // pick a door for the prize
    ixPrize = (int)(Math.random() * doors.length);
    doors[ixPrize] = true;
  }

  public void chooseDoor()
  {
    // contestant picks a door
    ixChosen = (int)(Math.random() * doors.length);
  }

  public void openNonWinningDoor()
  {
    ixDoorToOpen = ixChosen;
    // there's probably a way to do this without the loop
    // but I'm tired.
    while ( (ixDoorToOpen == ixChosen) || (doors[ixDoorToOpen]) )
    {
      ixDoorToOpen = (int)(Math.random() * doors.length);
    }
  }

  public void switchDoors()
  {
    ixNewChosen = ixChosen;
    while ( (ixNewChosen == ixChosen) || (ixNewChosen == ixDoorToOpen) )
    {
      ixNewChosen = (int)(Math.random() * doors.length);
    }
  }

  public static void main(String[] args)
  {
    try
    {
      int numDoors = Integer.parseInt(args[0]);
      int numReps = Integer.parseInt(args[1]);
      boolean switchDoors = (args.length == 3) && (args[2].equals("switch"));
      MontyHall mh = new MontyHall(numDoors);
      for ( int i = 0; i < numReps; i++ )
      {
        mh.reset();
        mh.placePrize();
        mh.chooseDoor();
        mh.openNonWinningDoor();
        if ( switchDoors )
        {
          mh.switchDoors();
          if ( mh.doors[mh.ixNewChosen] )
          {
            mh.results[ResultType.WIN.ordinal()]++;
          }
          else
          {
            mh.results[ResultType.LOSS.ordinal()]++;
          }
        }
        else
        {
          if ( mh.doors[mh.ixChosen] )
          {
            mh.results[ResultType.WIN.ordinal()]++;
          }
          else
          {
            mh.results[ResultType.LOSS.ordinal()]++;
          }
        }
      }
      System.out.printf( "%4d %4d %4d\n",
              numReps,
              mh.results[ResultType.WIN.ordinal()],
              mh.results[ResultType.LOSS.ordinal()]);
    }
    catch ( Exception e )
    {
      System.err.println( "Usage: MontyHall <num-doors> <num-reps> [\"switch\"]" );
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
