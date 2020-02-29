# Space-Explorers-and-Federation-HQ

->Objectives:

  Let there be a galaxy with space explorers and Federation  headquarters (HQs).
The goal of the space explorers is to explore all the wormholes so that they have a complete view of the galaxy.
The wormholes connect two star systems together, but entering them requires a perfect decoding of their
sub-space-mambo-jumbonian frequencies. Once a wormhole is explored, the federation sends a special science vessel 
through, which, if it survives, scans the new solar system and provides the data about the next wormholes and the
star systems they connect.
  The Federation has multiple HQs, but they can be busy with other tasks and, as such, they are not always available.
When a Federation HQ is available, it takes the decoded solution from the space explorers and sends the science vessel.
If the decoding is wrong when the science vessel tries to enter the worm-hole, a great explosion destroys the galaxy, 
kills everyone, ends the program, and the student is given 0 points.
  The solution is implemented in Java using threads. 
  I was responsible for implementing only the space explorers and the subspace communication channel between them and
the Federation HQs.

->Space Explorers and the Sub-space-mambo-jumbonian Frequencies:

  Each frequency decoding starts with data from the federation HQ, on which the science vessel uses an
analyzatron and computes the frequency as a seemingly random string.
  The space explorer then needs to decode the sub-space-mambo-jumbonian frequencies, in order to enter a wormhole.
To do this, the space explorer needs to compute the hash of the hash of the hash of ... of the hash of the frequency.
  The number of times to repeat the hash operation is fixed and given as a parameter to the simulation.
The space explorers start the simulation at a given set of star systems which can be reached with 
boring-slower-than-the-speed-of-light  drives. Then, they get the list of wormholes from the federation HQs.
Each wormhole has a frequency represented as a string sequence attached to it. An explorer chooses a wormhole and explores
it by decoding the frequency and then sending the result to the Federation HQs, which verify its correctness
by sending the science vessel.
  The space explorers are implemented in the SpaceExplorer.java file. I had to implement the constructor provided and I
could use the given methods to decode the sub-space-mambo-jumbonian frequencies.

->The Galaxy:
The galaxy is composed of many solar systems. One solar system can be connected through wormholes with
any number of other solar systems, whereas a few well-known solar systems can be accessed with a boring
slower-than-the-speed-of-light drive. There is no guarantee that any system can be reached from any other
system using this mesh of worm-holes. However, there is a guarantee that all interesting solar systems can
be reached if all wormholes are used. You can imagine the galaxy as a graph, where the solar systems are
the vertices and the wormholes are the edges that connect these vertices. The well-known solar systems are
the vertices of the graph that can be accessed at the start of the simulation. The space explorers need to
navigate through the wormholes and decode their frequencies in order to discover the entire galaxy.
It is possible to know where a wormhole will lead before it is decoded, so a science vessel can go through.
However, in order to find the other wormholes from the new solar system, the science vessel needs to go there.

->Federation HQs:

  Federation HQs are responsible not only for space exploration, but they each have a multitude of other tasks
and experiments to complete. Because everyone at the Federation HQ is super important and super busy,
it is possible to have large time periods when they simply do not answer. In this new society, bureaucracy
has reached new, previously undreamed-of levels, so it is not clear when the HQs stop answering and when
they start again.
When a Federation HQ does answer, it receives a wormhole’s decoded sub-space-mambo-jumbonian frequency from a
space explorer, along with the names of the two solar systems that are connected by said wormhole 
(i.e., the solar system that the space explorer has arrived from, and the solar system it currently
is located in).
  An incorrect decoding result will trigger an explosion, because everyone at the headquarters is too busy 
to double check and they just send the science vessel through.
Once the science vessel returns, the Federation gives the list of new wormholes to the space explorers,
so they can start work on the next decodings.
The Federation HQs are implemented in the HeadQuarter.java file.

->The Communication Channel:

  The space explorers and the Federation HQs communicate using a special bi-directional communication
channel. For every decoded wormhole frequency, the space explorers give the Federation HQs the following
information in a single message: new solar system, previous solar system, and the decoded string.
  For every decoded solution, the federation HQs give the space explorers the list of solar systems connected
through wormholes with the newly-accessible solar system.  If the federation HQ gets an incorrect decode or
an incorrect previous solar system, the science vessel enters the wormhole incorrectly and the entire galaxy
explodes and implodes at the same time.
  When there are no more wormholes to explore, the federation HQ puts EXIT messages in the communication channel,
one for each space explorer. They also trigger the end of the program.
After a wormhole’s frequency is correctly decoded, the Federation HQs give the space explorers the list
of new solar systems in separate messages that arrive in the following order:

discovered solar system
•
adjacent undiscovered solar system 1
•
discovered solar system
•
adjacent undiscovered solar system 2
•
...
•
END

  If two federation HQs give the space explorers this information at the same time,  it might confuse them.
Each of the adjacent solar system messages contains the string to be hashed by the space explorers repre-
senting the sub-space-mambo-jumbonian frequencies.
  The shape of the messages exchanged by the Federation HQs and the space explorers are
provided in the Message.java file.
  A message from a space explorer to an HQ contains the previous solar system, the current solar system, and the 
decoded frequency (in the “data” field).
  A message from an HQ to a space explorer contains a solar system and its undecoded frequency (in the “data” field).
The bi-directional communication channel needs to be implemented by you in the CommunicationChannel.java file. 
I had to implement the constructor and the methods provided.
