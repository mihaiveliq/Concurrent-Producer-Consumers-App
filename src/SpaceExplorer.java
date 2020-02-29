import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for a space explorer.
 */
public class SpaceExplorer extends Thread {
	// test files
	private static String NODE_NAMES = "_data.txt";
	private static String ANSWER = "_answer.txt";
	private static String GRAPH = "_graph.txt";

	// helper message (used when a solar system has no parent - it is reachable from
	// the start)
	private static String NO_PARENT = "NO_PARENT";

	/**
	 * Message body that specifies the end of the list of unlocked adjacent solar
	 * systems sent by the headquarters to the space explorers.
	 */
	public static String END = "END";

	/**
	 * Message body that specifies the end of the program (sent to each space
	 * explorer).
	 */
	public static String EXIT = "EXIT";

	// map of the galaxy (represented as a graph)
	private ArrayList<String> solarSystemNames;
	private ArrayList<String> solarSystemDecodedFrequencies;
	private Integer numberOfSolarSystems;
	private boolean[][] adjacencyMatrix;

	// termination
	private Integer numberOfSpaceExplorers;
	private AtomicInteger decodedFrequencies;
	private static Set<String> decodedFrequenciesSet = new HashSet<String>();

	// rec mess
	private Message recvMess;
	private String toEncrypt;

	// communications
	private CommunicationChannel channel;

	// hashCount
	private Integer hashCount;

	// discovered
	private Set<Integer> discovered;

	/*
	 * DO NOT MODIFY THIS FILE! IT WILL BE AUTOMATICALLY OVERWRITTEN BY THE CHECKER!
	 */
	/**
	 * Creates a {@code SpaceExplorer} object.
	 * 
	 * @param hashCount
	 *            number of times that a space explorer repeats the hash operation
	 *            when decoding
	 * @param discovered
	 *            set containing the IDs of the discovered solar systems
	 * @param channel
	 *            communication channel between the space explorers and the
	 *            headquarters
	 */
	public SpaceExplorer(Integer hashCount, Set<Integer> discovered, CommunicationChannel channel) {
		this.hashCount = hashCount;
		this.discovered = discovered;
		this.channel = channel;
	}

	@Override
	public void run() {
		while (true) {

			recvMess = channel.getMessageHeadQuarterChannel();

			if (recvMess.getData().equals(EXIT)) {
				break;
			} else if (recvMess.getData().equals(END)) {
				continue;
			}
			if (discovered.contains(recvMess.getCurrentSolarSystem())) {
				continue;
			}
			discovered.add(recvMess.getCurrentSolarSystem());
			toEncrypt = encryptMultipleTimes(recvMess.getData(), hashCount);
			channel.putMessageSpaceExplorerChannel(new Message(recvMess.getParentSolarSystem(),
					recvMess.getCurrentSolarSystem(), toEncrypt));
		}
	}
	
	/**
	 * Applies a hash function to a string for a given number of times (i.e.,
	 * decodes a frequency).
	 * 
	 * @param input
	 *            string to he hashed multiple times
	 * @param count
	 *            number of times that the string is hashed
	 * @return hashed string (i.e., decoded frequency)
	 */
	private String encryptMultipleTimes(String input, Integer count) {
		String hashed = input;
		for (int i = 0; i < count; ++i) {
			hashed = encryptThisString(hashed);
		}

		return hashed;
	}

	/**
	 * Applies a hash function to a string (to be used multiple times when decoding
	 * a frequency).
	 * 
	 * @param input
	 *            string to be hashed
	 * @return hashed string
	 */
	private static String encryptThisString(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

			// convert to string
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xff & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
