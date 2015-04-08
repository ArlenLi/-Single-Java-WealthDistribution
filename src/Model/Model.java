package Model;
import java.util.Random;
import java.util.Scanner;

import Component.*;

public class Model {
	protected int maxGrain;	 // to present the max num of grain of all patches
	protected int percentOfBestLand;
	protected int peopleNum;
	protected Patch position[][];	//to present the coordinate of the patch
	protected People people[];
    protected int currentTick;    // to present the current ticks
    protected int tick;
	protected int rangeXValue;	// the range of xValue of the coordinate
	protected int rangeYValue;	// the range of yValue of the coordinate
	protected int expectedMaxLife;	// the longest number of ticks that a person can possibly live 
	protected int expectedMinLife;	// the shortest number of ticks that a person can possibly live 
	protected int maxMetabolism;	// the highest possible amount of grain that a person could eat per clock tick
	protected int maxVision;
    protected int grainGrowthInterval;
    protected int numGrainGrown;

    // getter and setter
    public int getGrainGrowInterval() {
        return grainGrowthInterval;
    }

    public void setGrainGrowthInterval(int grainGrowInterval) {
        this.grainGrowthInterval = grainGrowInterval;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

	public int getMaxGrain() {
		return maxGrain;
	}

	public void setMaxGrain(int maxGrain) {
		this.maxGrain = maxGrain;
	}

	public int getPercentOfBestLand() {
		return percentOfBestLand;
	}

	public void setPercentOfBestLand(int percentOfBestLand) {
		this.percentOfBestLand = percentOfBestLand;
	}

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public Patch[][] getPosition() {
		return position;
	}

	public void setPosition(Patch[][] position) {
		this.position = position;
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public int getRangeXValue() {
		return rangeXValue;
	}

	public void setRangeXValue(int rangeXValue) {
		this.rangeXValue = rangeXValue;
	}

	public int getRangeYValue() {
		return rangeYValue;
	}

	public void setRangeYValue(int rangeYValue) {
		this.rangeYValue = rangeYValue;
	}

    public People[] getPeople() {
        return people;
    }

    public void setPeople(People[] people) {
        this.people = people;
    }

    public int getExpectedMaxLife() {
        return expectedMaxLife;
    }

    public void setExpectedMaxLife(int expectedMaxLife) {
        this.expectedMaxLife = expectedMaxLife;
    }

    public int getExpectedMinLife() {
        return expectedMinLife;
    }

    public void setExpectedMinLife(int expectedMinLife) {
        this.expectedMinLife = expectedMinLife;
    }

    public int getMaxMetabolism() {
        return maxMetabolism;
    }

    public void setMaxMetabolism(int maxMetabolism) {
        this.maxMetabolism = maxMetabolism;
    }

    public int getMaxVision() {
        return maxVision;
    }

    public void setMaxVision(int maxVision) {
        this.maxVision = maxVision;
    }

    //Constructor
	public Model(int percentOfBestLand, int peopleNum, int tick, int maxVision, int expectedMinLife, int expectedMaxLife,
			int rangeXValue, int rangeYValue, int maxGrain, int grainGrowthInterval, int numGrainGrown, int maxMetabolism) {
		this.percentOfBestLand = percentOfBestLand;
		this.peopleNum = peopleNum;
		this.tick = tick;
		this.rangeXValue = rangeXValue;
		this.rangeYValue = rangeYValue;
		this.maxGrain = maxGrain;
        this.grainGrowthInterval = grainGrowthInterval;
        this.numGrainGrown = numGrainGrown;
        this.maxMetabolism = maxMetabolism;
        this.maxVision = maxVision;
        this.expectedMinLife = expectedMinLife;
        this.expectedMaxLife = expectedMaxLife;
	}
	
	// initialize patches
	public void initialPatchs() {
		Patch p[][] = new Patch[2*rangeXValue+1][2*rangeYValue+1];
		Patch newPatch;
		for(int i=0; i<p.length; i++) {
			for (int j = 0; j < p[0].length; j++) {
				//give some patches the highest amount of grain possible, these patches are the "best land"
				double num = Math.random()*100;
				if (num <= percentOfBestLand ) {
					newPatch = new Patch(maxGrain,maxGrain,i,j);
				} else {
					newPatch = new Patch(0,0,i,j);
				}
				p[i][j] = newPatch;
			}
		}
		this.position = p;
	}
	
	//spread that grain around the window a little and put a little back into the patches that are the "best land" found above
	public void diffuseFirst() throws Exception{
		for(int k=0; k<5; k++) {
			for (int i=0; i<position.length; i++) {
				for(int j=0; j<position[0].length; j++) {
					if(position[i][j].getGrainHereMax() != 0) {
						position[i][j].setGrainHere(position[i][j].getGrainHereMax());
					}
					position[i][j].diffuse(position);
				}
			}
		}
	}
	
	//spread the grain around some more
	public void diffuseSecond() throws Exception {
		for(int k=0; k<10; k++) {
			for (int i=0; i<position.length; i++) {
				for(int j=0; j<position[0].length; j++) {					
					position[i][j].diffuse(position);					
				}
			}
		}
	}
	
	//round grain levels to whole numbers and initial grain level is also maximum
	public void setMaxGrainForAll() {
		for (int i=0; i<position.length; i++) {
			for(int j=0; j<position[0].length; j++) {					
				position[i][j].setGrainHere(Math.floor(position[i][j].getGrainHere()));
				position[i][j].setGrainHereMax((int)position[i][j].getGrainHere());
			}
		}
	}
	
//	public void printPatches() {
//		for (int i=0; i<position.length; i++) {
//			for(int j=0; j<position[0].length; j++) {
//				System.out.println(position[i][j]);
//			}
//		}
//	}
	
	//initialize people     *****************
	public void initialPeople() {
		people = new People[peopleNum];
		int age;
		Direction direction;
		int expectedLife;
		int metabolism;
		int vision;
		int wealth;
		Patch patch;
		Random random = new Random();
		for (int i=0; i<people.length; i++) {
			age = 0;
			direction = Direction.randomDirection();
			expectedLife = expectedMinLife + random.nextInt(expectedMaxLife - expectedMinLife + 1);
			metabolism = 1 + random.nextInt(maxMetabolism);
			wealth = metabolism + random.nextInt(50);
			vision = 1 + random.nextInt(maxVision);
			patch = position[random.nextInt(position.length)][random.nextInt(position[0].length)];
			patch.setPeopleHere(patch.getPeopleHere() + 1);
			age = random.nextInt(expectedLife);
			people[i] = new People(age, wealth, expectedLife, metabolism,
			vision, direction, patch);
		}
	}

    // people turn to the direction where the most grain lies and harvest
    public void peopleTUrnAndHarvest() {
        for (int i = 0; i < people.length; i++) {
            people[i].turnTowardsMostGrain(position);
            people[i].harvest();
        }
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[0].length; j++) {
                if(position[i][j].getPeopleHere() != 0) {
                    position[i][j].setGrainHere(0);
                    position[i][j].setPeopleHere(0);
                }
            }
        }
    }

    // all people move, eat, age and die
    public void peopleMoveEatAgeDie() {
        for (int i = 0; i < people.length; i++) {
            people[i].moveEatAgeDie(this);
        }
    }

    // sort people by the wealth by bubble sor
    public void sortPeople() {
        People temp;
        for (int i = 0; i < people.length - 1; i++) {
            for(int j = 0; j < people.length - 1 - i; j++) {
                if(people[j].getWealth() > people[j + 1].getWealth() ) {
                    temp = people[j];
                    people[j] = people[j + 1];
                    people[j + 1] = temp;
                }
            }
        }
    }

	// calculate the number of people in low, mid, up classes
    public int[] peopleClassCount() {
        int maxWealth = people[people.length - 1].getWealth();
        int lowCount = 0;
        int midCount = 0;
        int upCount = 0;
        for (int i = 0; i < people.length ; i++) {
            if(people[i].getWealth() <= maxWealth / 3){
                lowCount++;
            } else if(people[i].getWealth() <= maxWealth * 2 / 3) {
                midCount++;
            } else {
                upCount++;
            }
        }
        int[] classCount = new int[3];
        classCount[0] = lowCount;
        classCount[1] = midCount;
        classCount[2] = upCount;
        return classCount;
    }

	// calculate the gini index
	public double getGiniIndex() {
		int wealthTotal = 0;
		int wealthSumSoFar = 0;
		double giniIndex = 0;
		for (int i = 0; i < people.length; i++) {
			wealthTotal += people[i].getWealth();
		}
		int i = 0;
		while(i < people.length) {
			wealthSumSoFar += people[i].getWealth();
			i++;
			giniIndex = giniIndex + (((double)i/peopleNum) - ((double)wealthSumSoFar/wealthTotal))*0.01;
		}
		return giniIndex;
	}


	
	
	public static void main(String args[]) throws Exception {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter the number of percentOfBestLand:");
//		int percentOfBestLand = sc.nextInt();
//		System.out.println("Please enter the number of people:");
//		int peopleNum = sc.nextInt();
//		System.out.println("Please enter the number of ticks that you want to run:");
//		int tick = sc.nextInt();
//		System.out.println("Please enter the range of xValue of the coordinate:");  // ?
//		int rXValue = sc.nextInt();
//		System.out.println("Please enter the range of yValue of the coordinate:");	//?
//		int rYValue = sc.nextInt();
//		System.out.println("Please enter the number of maxGrain:");	//?
//		int maxGrain = sc.nextInt();
//        System.out.println("Please enter the number of grain growth interval:");	//?
//        int grainGrowthInterval = sc.nextInt();
//        System.out.println("Please enter the number of grain grown:");	//?
//        int numGrainGrown = sc.nextInt();
//        System.out.println("Please enter the number of maxMetabolism:");	//?
//        int maxMetabolism = sc.nextInt();
//        System.out.println("Please enter the number of maxVision:");	//?
//        int maxVision = sc.nextInt();
//        System.out.println("Please enter the number of expectedMinLife:");	//?
//        int expectedMinLife = sc.nextInt();
//        System.out.println("Please enter the number of expectedMaxLife:");	//?
//        int expectedMaxLife = sc.nextInt();

        // initialize the model
//		Model model = new Model(percentOfBestLand, peopleNum, tick, maxVision, rXValue, rYValue, maxGrain, grainGrowthInterval,
//              numGrainGrown, maxMetabolism);
        Model model = new Model(10, 250, 100, 5, 1, 83, 25, 25, 50, 1, 4, 15);
        // initialize patches
		model.initialPatchs();
		
		// spread that grain around the window a little and put a little back into the patches that are the "best land" found above
		model.diffuseFirst();
		model.diffuseSecond();
		model.setMaxGrainForAll();

        // initialize people
        model.initialPeople();

        for(model.currentTick = 0; model.currentTick < model.getTick(); model.currentTick++) {

            model.peopleTUrnAndHarvest();
            model.peopleMoveEatAgeDie();
            if((model.currentTick % model.grainGrowthInterval) == 0 ) {
                for (int i = 0; i < model.position.length; i++) {
                    for(int j =0; j < model.position[0].length; j++) {
                        model.position[i][j].growGrain(model.numGrainGrown);
                    }
                }
            }
			model.sortPeople(); // sort people by wealth
			int[] classCount = model.peopleClassCount();
			for (int i = 0; i < classCount.length; i++) {
				System.out.print(classCount[i] + " ");
			}
			System.out.println(model.getGiniIndex());
			model.getGiniIndex();
        }
	}
}
