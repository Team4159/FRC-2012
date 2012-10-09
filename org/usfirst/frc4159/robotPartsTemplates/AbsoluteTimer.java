package org.usfirst.frc4159.robotPartsTemplates;

public class AbsoluteTimer {
	private long debtTime = 0;
	private long perLoopTime = 0;
	private long startTime = 0;

	public AbsoluteTimer (long plt)
	{
		perLoopTime = plt;
	}

	public void startDelayedCode ()
	{
		startTime = System.currentTimeMillis ();
	}

	public void endDelayedCode ()
	{
		long currentEndTime = System.currentTimeMillis ();
		long expectedEndTime = startTime + perLoopTime;
		long extraTime = expectedEndTime - currentEndTime;

		if (extraTime > 0)
		{
			if (extraTime > debtTime)
			{
				extraTime -= debtTime;
				debtTime = 0;
				realSleep (extraTime);
			}
			else
			{
				debtTime -= extraTime;
			}
		}
		else
		{
			debtTime += -extraTime;
		}
	}

	private static void realSleep (long ms)
	{
		long now = System.currentTimeMillis ();
		long end = now + ms;

		do {
			try {
				Thread.sleep (end - now);
			} catch (InterruptedException e) {}
			now = System.currentTimeMillis ();
		} while (now < end);
	}

}
