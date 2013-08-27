import java.util.ArrayList;
import java.util.List;

/*
Class courtship 1.1
1. Need to handle get no response situation, since did not get response.
2. Should store every output to files, keep the records.
3. Put Human fields in to class become attributes, because they should be. 
   Do not want to pass them to functions every time.
4. Add annotations make the code more readable. 
5. Test affectiveLevel before invoking askOut.

Will do
1. sync affectiveLevel with functions in courtship;
2. need more information to implement changeAffectiveLevelOfResponser, please give me some feedback. 
*/
public class Courtship{
	//two Human in courtship, static because all the entities of this class will be the same name
	private static Human responser;
	private static Human requester;
	
	//the affectiveLevel of two Human, same reason use static
	private static AffectiveLevel affectiveLevelOfResponser;
	private static AffectiveLevel affectiveLevelOfRequester;
	
	//use to store affective memories
	Storage affectiveMemories = new Storage();

	// just access methods, ignore these, have to make attributes private
	public static Human getResponser() {
		return responser;
	}
	
	public static void setResponser(Human responser) {
		Courtship.responser = responser;
	}

	public static Human getRequester() {
		return requester;
	}

	public static void setRequester(Human requester) {
		Courtship.requester = requester;
	}

	public static AffectiveLevel getAffectiveLevelOfResponser() {
		return affectiveLevelOfResponser;
	}

	public static void setAffectiveLevelOfResponser(AffectiveLevel affectiveLevelOfResponser) {
		Courtship.affectiveLevelOfResponser = affectiveLevelOfResponser;
	}

	public static AffectiveLevel getAffectiveLevelOfRequester() {
		return affectiveLevelOfRequester;
	}

	public static void setAffectiveLevelOfRequester(AffectiveLevel affectiveLevelOfRequester) {
		Courtship.affectiveLevelOfRequester = affectiveLevelOfRequester;
	}

	
	//handle ResponserExn, include no response.
	public static class ResponserExn extends Exception {
		private static final long serialVersionUID = 1L;
		public ResponserExn(String msg){
			super(msg);
		}
		
	}
	
	//function for askOut
	public boolean askOut(Time beginning, Location location, String reason) throws ResponserExn{
		IdleTime idleTime = requester.requestForIdleTime(beginning, responser);
		if (idleTime == null){						//handle get no response, maybe should find out why
			//add the result of askOut to memory
			affectiveMemories.addAskOutToMemory(beginning, location, reason, idleTime);
			throw new ResponserExn("Got no reponse from responser");
		}
		else if (!idleTime.isEmpty){				//handle responser has idleTime
			boolean answer = responser.spendIdleTime(requester, idleTime, location, reason);
			affectiveMemories.addAskOutToMemory(beginning, location, reason, idleTime);
			return answer;
		}
		else{										//handle responser do not has idleTime
			Time someOtherTime = new Time("nextAvaiableTime");
			requester.addAskOutToRunLoop(someOtherTime, beginning, location, reason);
			affectiveMemories.addAskOutToMemory(beginning, location, reason, idleTime);
			return false;
		}
		
	}
	
	//function for requester want to walk responser home
	public boolean walkHome(Time beginning, String note) throws ResponserExn{
		IdleTime idleTime = requester.requestForWalkHome(beginning, note, responser);
		if (idleTime == null){						//handle get no response, maybe should find out why
			//add the result of askOut to memory
			affectiveMemories.addWalkHomeToMemory(beginning, note, idleTime);
			throw new ResponserExn("Got no reponse from responser");
		}
		else if (!idleTime.isEmpty){				//handle responser said yes
			affectiveMemories.addWalkHomeToMemory(beginning, note, idleTime);
			return true;
		}
		else{										//handle responser said no
			Time someOtherTime = new Time("nextAvaiableTime");
			requester.addWalkHomeToRunLoop(someOtherTime, beginning, note);
			affectiveMemories.addWalkHomeToMemory(beginning, note, idleTime);
			return false;
		}
		
	}
	//according event, change affectiveLevel of requester
	public void changeAffectiveLevelOfRequester(Event event){
		String eventName = event.getEventName();
		switch (eventName) {
		case "work hard in the library":
			affectiveLevelOfRequester.increaseAffectiveLevel("Hign");
			affectiveMemories.addEventToMemory(event);
			break;
		case "love studing":
			affectiveLevelOfRequester.increaseAffectiveLevel("Hign");
			break;
		case "beautiful smile":
			affectiveLevelOfRequester.increaseAffectiveLevel("Hign");
			break;
		case "sweet voice":
			affectiveLevelOfRequester.increaseAffectiveLevel("Medium");
			break;
		case "like design and creative things":
			affectiveLevelOfRequester.increaseAffectiveLevel("Hign");
			break;
		case "have great time when walk you home":
			affectiveLevelOfRequester.increaseAffectiveLevel("Hign");
			break;
		case "do not give a response":
			affectiveLevelOfRequester.decreaseAffectiveLevel("Low");
			break;
		default:
			break;
		}
	}
	
	public void changeAffectiveLevelOfResponder(Event event){
		//need to know
	}
	
	//using today's event, change affectiveLevel
	public void changeDailyAffectiveLevel(Courtship courtship){
		List<Event> eventList = new ArrayList<Event>();
		for (int i=0; i<eventList.size(); i++){
			courtship.changeAffectiveLevelOfRequester(eventList.get(i));
		}
	}
	
	//just do nothing
	private void doNothing() {
		
	}
	
	public static void main(String[] args) throws ResponserExn {
		//initialize Courtship 
		Courtship courtship = new Courtship();
		Courtship.setResponser(new Human("Yang Zhen"));
		Courtship.setRequester(new Human("Luo Xi"));
		Courtship.setAffectiveLevelOfRequester(new AffectiveLevel());
		Courtship.setAffectiveLevelOfResponser(new AffectiveLevel());
		
		//Test affectiveLevel until requester makes sure he love the responder. 
		//If makeSureFeeling is false, keep doing nothing until makeSureFeeling() == true. 
		//start to do next lines when makeSureFeeling() == true.
		while (Courtship.getAffectiveLevelOfRequester().makeSureFeeling() == false){
			courtship.changeDailyAffectiveLevel(courtship);
			courtship.doNothing();
		}
		
		//happened next
		courtship.walkHome(new Time("Sep 21,2012"), "Want to");						//return true
		courtship.walkHome(new Time("Sep 25,2012"), "Really want to");				//return false
		courtship.walkHome(new Time("Sep 26,2012"), "Really want to");				//return true
		courtship.walkHome(new Time("Sep 27,2012"), "Really Really want to");		//return false
		courtship.askOut(new Time("Sep 28,2012 After Fanpingping's video clip"), 
						 new Location("Walk along the Hudson River"), 
						 "You do not like the Mid-Autumn Festival Gala");			//got no response
		courtship.walkHome(new Time("Sep 30,2012"),"I want to ask but do not know it is appropriate or not");	//return true
		courtship.walkHome(new Time("Oct 2"), null);								//return false
		courtship.walkHome(new Time("Oct 3"), "Walk with you and other people");	//return true
		
		
		//to be continued...

		
	}

	
}

