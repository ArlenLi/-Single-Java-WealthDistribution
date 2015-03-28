package Component;
import Model.*;
import java.util.Random;
import static Component.Direction.*;

public class People {

    protected int age;
    protected int wealth;
    protected int expectedLife;
    protected int metabolism;
    protected int vision;
    protected Direction direction;	// to present which direction the person faces, Direction is an enum class
    protected Patch onPatch; // to present which patch the person stands now

    //getter and setter
    public Patch getOnPatch() {
        return onPatch;
    }

    public void setOnPatch(Patch onPatch) {
        this.onPatch = onPatch;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getExpectedLife() {
        return expectedLife;
    }

    public void setExpectedLife(int expectedLife) {
        this.expectedLife = expectedLife;
    }

    public int getMetabolism() {
        return metabolism;
    }

    public void setMetabolism(int metabolism) {
        this.metabolism = metabolism;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //Constructor
	public People(int age, int wealth, int expectedLife, int metabolism,
			int vision, Direction direction, Patch onPatch) {
		this.age = age;
		this.wealth = wealth;
		this.expectedLife = expectedLife;
		this.metabolism = metabolism;
		this.vision = vision;
		this.direction = direction;
		this.onPatch = onPatch;
	}

    // choose direction holding most grain within the turtle's vision
    public void turnTowardsMostGrain(Patch[][] p) {
        Direction dir = NORTH;
        Direction bestDir = NORTH;
        int mostGrain = this.grainAhead(p,dir);
        dir = EAST;
        if(this.grainAhead(p,dir) > mostGrain) {
            bestDir = dir;
            mostGrain = grainAhead(p,dir);
        }
        dir = SOUTH;
        if(this.grainAhead(p,dir) > mostGrain) {
            bestDir = dir;
            mostGrain = grainAhead(p,dir);
        }
        dir = WEST;
        if(this.grainAhead(p,dir) > mostGrain) {
            bestDir = dir;
            mostGrain = grainAhead(p,dir);
        }
        this.setDirection(bestDir);
    }

    // calculate the amount of grain on one direction of person within the person's vision
    public int grainAhead(Patch[][] p, Direction d) {
        int total = 0;
        int howFar;
        switch(d) {
            case NORTH:
                for (howFar = 1; howFar <= this.getVision(); howFar++) {
                    if((this.getOnPatch().getyValue()+ howFar) >= p[0].length) {
                        return total;
                    } else {
                        total += p[this.getOnPatch().getxValue()][this.getOnPatch().getyValue() + howFar].getGrainHere();
                    }
                }
                break;
            case EAST:
                for (howFar = 1; howFar <= this.getVision(); howFar++) {
                    if((this.getOnPatch().getxValue()+ howFar) >= p[0].length) {
                        return total;
                    } else {
                        total += p[this.getOnPatch().getxValue() + howFar][this.getOnPatch().getyValue()].getGrainHere();
                    }
                }
                break;
            case SOUTH:
                for(howFar = 1; howFar <= this.getVision();howFar++) {
                    if((this.getOnPatch().getyValue() - howFar) < 0) {
                        return total;
                    } else {
                        total += p[this.getOnPatch().getxValue()][this.getOnPatch().getyValue() - howFar].getGrainHere();
                    }
                }
                break;
            case WEST:
                for (howFar = 1; howFar <= this.getVision(); howFar++) {
                    if((this.getOnPatch().getxValue() - howFar) < 0) {
                        return total;
                    } else {
                        total += p[this.getOnPatch().getxValue() - howFar][this.getOnPatch().getyValue()].getGrainHere();
                    }
                }
                break;
        }
        return total;
    }

    // each turtle harvests the grain on its patch.  if there are multiple turtles on a patch,
    //divide the grain evenly among the turtles
    public void harvest() {
        this.wealth += (int)Math.floor(this.getOnPatch().getGrainHere() / this.getOnPatch().getPeopleHere());
    }

    public void moveEatAgeDie(Model m) {

        // move
        /*
        particular situation: the grain amount of patches around the
        person are all 0 and the person can not forward 1 step towards NORTH
        */
        if (direction == NORTH && ((onPatch.getyValue() + 1) >= m.getPosition()[0].length)) {
            onPatch = this.getOnPatch();
            onPatch.setPeopleHere(onPatch.getPeopleHere() + 1);
        } else {
            switch(direction) {
                case NORTH:
                    onPatch = m.getPosition()[onPatch.getxValue()][onPatch.getyValue() + 1];
                    onPatch.setPeopleHere(onPatch.getPeopleHere() + 1);
                    break;
                case EAST:
                    onPatch = m.getPosition()[onPatch.getxValue() + 1][onPatch.getyValue()];
                    onPatch.setPeopleHere(onPatch.getPeopleHere() + 1);
                    break;
                case SOUTH:
                    onPatch = m.getPosition()[onPatch.getxValue()][onPatch.getyValue() - 1];
                    onPatch.setPeopleHere(onPatch.getPeopleHere() + 1);
                    break;
                case WEST:
                    onPatch = m.getPosition()[onPatch.getxValue() - 1][onPatch.getyValue()];
                    onPatch.setPeopleHere(onPatch.getPeopleHere() + 1);
                    break;
            }
        }

        //eat
        wealth -= metabolism;

        //grow order
        age += 1;

         /*
         check for death conditions: if you have no grain or
         you're older than the life expectancy or if some random factor
         holds, then you "die" and are "reborn" (in fact, your variables
         are just reset to new random values)
         */
        if((wealth < 0) || age >= expectedLife) {
            Random random = new Random();
            age = 0;
            direction = Direction.randomDirection();
            expectedLife = m.getExpectedMinLife() + random.nextInt(m.getExpectedMaxLife() - m.getExpectedMinLife() + 1);
            metabolism = 1 + random.nextInt(m.getMaxMetabolism());
            wealth = metabolism + random.nextInt(50);
            vision = 1 + random.nextInt(m.getMaxVision());
        }
    }


}
