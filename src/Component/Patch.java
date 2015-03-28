package Component;

public class Patch {
	protected double grainHere;	 // to present the grain num of this patch
	protected int grainHereMax;	// to present the max grain num of this patch
	protected int peopleHere = 0;	// to present the number of people on this patch
	protected int xValue;	// to present the xValue of the coordinate
	protected int yValue;	// to present the yValue of the coordinate

    //getter and setter
    public double getGrainHere() {
        return grainHere;
    }

    public void setGrainHere(double grainHere) {
        this.grainHere = grainHere;
    }

    public int getGrainHereMax() {
        return grainHereMax;
    }

    public void setGrainHereMax(int grainHereMax) {
        this.grainHereMax = grainHereMax;
    }

    public int getPeopleHere() {
        return peopleHere;
    }

    public void setPeopleHere(int peopleHere) {
        this.peopleHere = peopleHere;
    }

    public int getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    //Constructor
	public Patch(double grainHere, int grainHereMax, int xValue, int yValue) {
		this.grainHere = grainHere;
		this.grainHereMax = grainHereMax;
		this.xValue = xValue;
		this.yValue = yValue;
	}
	

	
	
	@Override
	public String toString() {
		return "Patch [grainHere=" + grainHere + ", grainHereMax="
				+ grainHereMax + ", peopleHere=" + peopleHere + ", xValue="
				+ xValue + ", yValue=" + yValue + "]";
	}

	//spread that grain around the window a little
	public void diffuse(Patch[][] p) throws Exception {
		double diffuseNum = grainHere * 0.25 * 0.125;
		if(xValue - 1 < 0) {
			if (yValue - 1 < 0) {
				p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
				p[xValue+1][yValue+1].setGrainHere(p[xValue+1][yValue+1].getGrainHere()+diffuseNum);
				p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
				grainHere = grainHere - (3 * diffuseNum); 
			}
			else if (yValue + 1 > p[0].length - 1) {
				p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
				p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
				p[xValue+1][yValue-1].setGrainHere(p[xValue+1][yValue-1].getGrainHere()+diffuseNum);
				grainHere = grainHere - (3 * diffuseNum); 
			} 
			else{
			p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
			p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue+1].setGrainHere(p[xValue+1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
			p[xValue+1][yValue-1].setGrainHere(p[xValue+1][yValue-1].getGrainHere()+diffuseNum);
			grainHere = grainHere - (5 * diffuseNum);
			}
		}
		else if(xValue + 1 > p.length - 1) {
			if (yValue - 1 < 0) {
				p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
				p[xValue-1][yValue+1].setGrainHere(p[xValue-1][yValue+1].getGrainHere()+diffuseNum);
				p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
				grainHere = grainHere - (3 * diffuseNum); 
			}
			else if (yValue + 1 > p[0].length - 1) {
				p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
				p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
				p[xValue-1][yValue-1].setGrainHere(p[xValue-1][yValue-1].getGrainHere()+diffuseNum);
				grainHere = grainHere - (3 * diffuseNum); 
			} 
			else {
			p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
			p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue+1].setGrainHere(p[xValue-1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
			p[xValue-1][yValue-1].setGrainHere(p[xValue-1][yValue-1].getGrainHere()+diffuseNum);
			grainHere = grainHere - (5 * diffuseNum); 
			}
		}
		else if (yValue + 1 > p[0].length - 1) {
			p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue-1].setGrainHere(p[xValue-1][yValue-1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue-1].setGrainHere(p[xValue+1][yValue-1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
			p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
			grainHere = grainHere - (5 * diffuseNum); 
		}
		else if (yValue - 1 < 0) {
			p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue+1].setGrainHere(p[xValue-1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue+1].setGrainHere(p[xValue+1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
			p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
			grainHere = grainHere - (5 * diffuseNum); 
		}
		else {
			p[xValue][yValue+1].setGrainHere(p[xValue][yValue+1].getGrainHere()+diffuseNum);
			p[xValue][yValue-1].setGrainHere(p[xValue][yValue-1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue+1].setGrainHere(p[xValue-1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue-1][yValue].setGrainHere(p[xValue-1][yValue].getGrainHere()+diffuseNum);
			p[xValue-1][yValue-1].setGrainHere(p[xValue-1][yValue-1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue+1].setGrainHere(p[xValue+1][yValue+1].getGrainHere()+diffuseNum);
			p[xValue+1][yValue].setGrainHere(p[xValue+1][yValue].getGrainHere()+diffuseNum);
			p[xValue+1][yValue-1].setGrainHere(p[xValue+1][yValue-1].getGrainHere()+diffuseNum);
			grainHere = grainHere - (8 * diffuseNum); 
		}
	}
	
	
//	//spread that grain around the window a little and put a little back into the patches that are the "best land" found above just once
//	public void diffuseFirst(Patch[][] p) throws Exception {
//		for (int i=0; i<p.length; i++) {
//			for(int j=0; j<p[0].length; j++) {
//				if(p[i][j].getGrainHereMax() != 0) {
//					p[i][j].setGrainHere(p[i][j].getGrainHereMax());
//					diffuse(p);
//				}
//			}
//		}
//	}

	//patch procedure
    public void growGrain(int numGrainGrown) {
		//if a patch does not have it's maximum amount of grain, add num-grain-grown to its grain amount
        if (grainHere < grainHereMax) {
            grainHere = grainHere + numGrainGrown;
			//if the new amount of grain on a patch is over its maximum capacity, set it to its maximum
            if (grainHere > grainHereMax) {
                grainHere = grainHereMax;
            }
        }
    }
}
