package org.moppa.MoppaCore;

import org.moppa.MoppaCore.model.Task;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple TaskManagerMock.
 */
public class TaskManagerTest extends TestCase
{
	private TaskManager taskManager;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TaskManagerTest( String testName )
    {
        super( testName );
        taskManager = new TaskManagerMock();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TaskManagerTest.class );
    }

    /**
     * Create task
     */
    public void testCreateTask()
    {
    	int factorialNumber = 5;
    	Task task = taskManager.createTask(factorialNumber, 4434);
    	
    	assertEquals( factorialNumber, task.getnValue() );
    }
    
    /**
     * Check task status
     */
    public void testCheckTask()
    {
    	Task task = taskManager.checkTask(12345);
    	assertNotNull( task );
    	assertNotNull( task.getStatus() );
    }
    
    /**
     * Try to get task for device
     */
    public void testTryGetTask()
    {
    	Task task = taskManager.tryGetTask("67890");
    	if (task != null) {
    		//now status should change to "in progress", deviceId to given value or return NULL
    		assertEquals( "67890", task.getDeviceId() );
    	}
    }
    
    /**
     * Save result of calculation
     */
    public void testSaveTaskResult()
    {
    	boolean wasSaved = taskManager.saveTaskResult("67890", "231423");
    	assertEquals( true , wasSaved );
    }
}
