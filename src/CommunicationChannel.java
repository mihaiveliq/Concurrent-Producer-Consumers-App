import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that implements the channel used by headquarters and space explorers to communicate.
 */
public class CommunicationChannel {
	private ArrayBlockingQueue<Message> toHQ;
	private ArrayBlockingQueue<Message> toSE;
	int HQSecondLock;
	int SESecondLock;
	int parentId;
	int nrSystems = 100000;
	ReentrantLock locker;
	ReentrantLock newlocker;
	/**
	 * Creates a {@code CommunicationChannel} object.
	 */
	public CommunicationChannel() {
		locker = new ReentrantLock();
		newlocker = new ReentrantLock();
		toSE = new ArrayBlockingQueue<>(nrSystems * 2, true);
		toHQ = new ArrayBlockingQueue<>(nrSystems * 2, true);
		HQSecondLock = 0;
		SESecondLock = 0;
		parentId = 0;
	}

	/**
	 * Puts a message on the space explorer channel (i.e., where space explorers write to and 
	 * headquarters read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 */
	public void putMessageSpaceExplorerChannel(Message message) {
		try {
			toHQ.put(message);
		} catch(InterruptedException e) {
		}
	}

	/**
	 * Gets a message from the space explorer channel (i.e., where space explorers write to and
	 * headquarters read from).
	 * 
	 * @return message from the space explorer channel
	 */
	public Message getMessageSpaceExplorerChannel() {
		Message message = null;

		try {
			message = toHQ.take();
		} catch(InterruptedException e) {
		}

		return message;
	}

	/**
	 * Puts a message on the headquarters channel (i.e., where headquarters write to and 
	 * space explorers read from).
	 * 
	 * @param message
	 *            message to be put on the channel
	 */
	public void putMessageHeadQuarterChannel(Message message) {
		if (message.getData().equals("END")) {
			return;
		}

		newlocker.lock();

		if (message.getData().equals("EXIT")) {
			try {
				toSE.put(message);
			} catch(InterruptedException e) {
			}

			newlocker.unlock();

			return;
		}
		SESecondLock += 1;
		if (SESecondLock < 2) {
			parentId = message.getCurrentSolarSystem();
		}

		if (SESecondLock == 2) {
			message.setParentSolarSystem(parentId);
			try {
				toSE.put(message);
			} catch(InterruptedException e) {
			}
			newlocker.unlock();
			newlocker.unlock();
			SESecondLock = 0;
		}
	}

	/**
	 * Gets a message from the headquarters channel (i.e., where headquarters write to and
	 * space explorer read from).
	 * 
	 * @return message from the header quarter channel
	 */
	public Message getMessageHeadQuarterChannel() {
		Message message = null;

			try {
				message = toSE.take();
			} catch (InterruptedException e) {
			}

		return message;
	}
}
