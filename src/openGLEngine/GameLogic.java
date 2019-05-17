package openGLEngine;

//Contains Collision Detection, AI, What Ever Else
public class GameLogic {
	
	/**
	 * Determines if the collisionHull is an AABB
	 * @param c
	 * @return
	 */
	private static boolean isAABB(collisionHull c)
	{
		if(c==null)
		{
			return false;
		}
		else
		{
			return (c.getClass() == Box.class);
		}
	}
	
	/**
	 * Finds a collision between the two collision hulls and determines if there
	 * is a collision. If there is no method to deal with both collision hulls, it
	 * returns false.
	 * @param o
	 * @param other
	 * @return
	 */
	public static boolean getCollision(collisionHull o, collisionHull other)
	{
		boolean value = false;
		
		if( o.getClass().equals(Box.class) )
		{
			if( other.getClass().equals(Box.class) )
			{
				//Box to Box
				value = getCollisionAABB((Box) o, (Box) other);
			}
			else if( other.getClass().equals(Circle.class) )
			{
				//Box to Circle
				value = getCollisionBoxToCircle((Box) o, (Circle) other);
			}
			else if( other.getClass().equals(Line.class) )
			{
				//Box to Line
				value = getCollisionBoxToLine((Box) o, (Line) other);
			}
			else if( other.getClass().equals(Point.class) )
			{
				//Box to Point
				value = getCollisionBoxToPoint((Box) o, (Point) other);
			}
			else if( other.getClass().equals(Triangle.class) )
			{
				//Box to Triangle
				value = getCollisionBoxToTriangle((Box) o, (Triangle) other);
			}
			else
			{
				System.err.println("Can't find a collision method for the second object.");
			}
		}
		else if( o.getClass().equals(Circle.class) )
		{
			if( other.getClass().equals(Box.class) )
			{
				//Circle to Box
				value = getCollisionBoxToCircle((Box) other, (Circle) o);
			}
			else if( other.getClass().equals(Circle.class) )
			{
				//Circle to Circle
				value = getCollisionCircleToCircle((Circle) o, (Circle) other);
			}
			else if( other.getClass().equals(Line.class) )
			{
				//Circle to Line
			}
			else if( other.getClass().equals(Point.class) )
			{
				//Circle to Point
				value = getCollisionCircleToPoint( (Circle) o, (Point) other);
			}
			else if( other.getClass().equals(Triangle.class) )
			{
				//Circle to Triangle
				value = getCollisionTriangleToCircle( (Triangle) other, (Circle) o);
			}
			else
			{
				System.err.println("Can't find a collision method for the second object.");
			}
		}
		else if( o.getClass().equals(Line.class) )
		{
			if( other.getClass().equals(Box.class) )
			{
				//Line to Box
				value = getCollisionBoxToLine((Box)other, (Line)o);
			}
			else if( other.getClass().equals(Circle.class) )
			{
				//Line to Circle
			}
			else if( other.getClass().equals(Line.class) )
			{
				//Line to Line
				value = getCollisionLineToLine((Line) o, (Line) other);
			}
			else if( other.getClass().equals(Point.class) )
			{
				//Line to Point
				value = getCollisionLineToPoint((Line) o, (Point) other);
			}
			else if( other.getClass().equals(Triangle.class) )
			{
				//Line to Triangle
				value = getCollisionTriangleToLine((Triangle) other, (Line) o);
			}
			else
			{
				System.err.println("Can't find a collision method for the second object.");
			}
		}
		else if( o.getClass().equals(Point.class) )
		{
			if( other.getClass().equals(Box.class) )
			{
				//Point to Box
				value = getCollisionBoxToPoint((Box) other, (Point) o);
			}
			else if( other.getClass().equals(Circle.class) )
			{
				//Point to Circle
				value = getCollisionCircleToPoint((Circle) other, (Point) o);
			}
			else if( other.getClass().equals(Line.class) )
			{
				//Point to Line
				value = getCollisionLineToPoint((Line) other, (Point) o);
			}
			else if( other.getClass().equals(Point.class) )
			{
				//Point to Point
				value = getCollisionPointToPoint((Point) o, (Point) other);
			}
			else if( other.getClass().equals(Triangle.class) )
			{
				//Point to Triangle
				value = getCollisionTriangleToPoint( (Triangle) other, (Point) o);
			}
			else
			{
				System.err.println("Can't find a collision method for the second object.");
			}
		}
		else if( o.getClass().equals(Triangle.class) )
		{
			if( other.getClass().equals(Box.class) )
			{
				//Triangle to Box
				value = getCollisionBoxToTriangle((Box)other, (Triangle)o);
			}
			else if( other.getClass().equals(Circle.class) )
			{
				//Triangle to Circle
				value = getCollisionTriangleToCircle((Triangle) o, (Circle) other);
			}
			else if( other.getClass().equals(Line.class) )
			{
				//Triangle to Line
				value = getCollisionTriangleToLine((Triangle) o, (Line) other);
			}
			else if( other.getClass().equals(Point.class) )
			{
				//Triangle to Point
				value = getCollisionTriangleToPoint((Triangle)o, (Point)other);
			}
			else if( other.getClass().equals(Triangle.class) )
			{
				//Triangle to Triangle
				value = getCollisionTriangleToTriangle((Triangle)o, (Triangle)other);
			}
			else
			{
				System.err.println("Can't find a collision method for the second object.");
			}
		}
		else
		{
			System.err.println("Can't find a collision method for the first object.");
		}
		
		return value;
	}
	
	/**
	 * Determines if there is a collision between the two game objects. It is a wrapper
	 * around the original getCollision() method and behaves in the same way.
	 * @param o
	 * @param other
	 * @return
	 */
	public static boolean getCollision(parentGameObject o, parentGameObject other)
	{
		return getCollision(o.getCollisionHull(), other.getCollisionHull());
	}
	
	/**
	 * Determines if two boxes are colliding
	 * 
	 * Not perfect, if the Box b2 is inside of the Box b1, it will not
	 * detect the collision. Switching the order of b1 and b2 will have
	 * the same issue but reverse.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionAABB(Box b1, Box b2)
	{
		if ( b1.getMaxX() >= b2.getMinX()
			&& b1.getMinX() <= b2.getMaxX() )
		{
			if ( b1.getMaxY() >= b2.getMinY()
				&& b1.getMinY() <= b2.getMaxY() )
			{
				return true;
			}
		}

		return false;
	}
	
	public static boolean getCollisionBoxToLine(Box b1, Line b2)
	{
		if(getCollisionBoxToPoint(b1, new Point(b2.getStartX(), b2.getStartY())))
		{
			return true;
		}
		else if(getCollisionBoxToPoint(b1, new Point(b2.getEndX(), b2.getEndY())))
		{
			return true;
		}
		else
		{
			if(b2.getMinX()<b1.getLeftBound() && b2.getMaxX()>b1.getRightBound())
			{
				if(b2.getMaxY()>=b1.getTopBound() && b2.getMinY()<=b1.getBottomBound())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Determines if a point is inside a box.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionBoxToPoint(Box b1, Point b2)
	{
		if(b2.x >= b1.getMinX() && b2.x <= b1.getMaxX())
		{
			if(b2.y >= b1.getMinY() && b2.y <= b1.getMaxY())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines if a box collides with a circle
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionBoxToCircle(Box b1, Circle b2)
	{
		//clamp x and y values to the edge of the axis aligned box.
		double xValue = GameMath.clamp( b2.getX(), b1.getLeftBound(), b1.getRightBound() );
		double yValue = GameMath.clamp( b2.getY(), b1.getTopBound(), b1.getBottomBound() );
		
		//determine if point is in circle.
		return getCollisionCircleToPoint( b2, new Point(xValue, yValue) );
	}
	
	/**
	 * Determines if box is colliding with a triangle.
	 * (Subjective to change)
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionBoxToTriangle(Box b1, Triangle b2)
	{
		
		if( getCollisionBoxToPoint(b1, new Point(b2.getX1(), b2.getY1()) ) )
		{
			return true;
		}
		
		if( getCollisionBoxToPoint(b1, new Point(b2.getX2(), b2.getY2()) ) )
		{
			return true;
		}
		
		if( getCollisionBoxToPoint(b1, new Point(b2.getX3(), b2.getY3()) ) )
		{
			return true;
		}
		
		if( getCollisionTriangleToPoint(b2, new Point(b1.getLeftBound(), b1.getTopBound()) ) )
		{
			return true;
		}
		
		if( getCollisionTriangleToPoint(b2, new Point(b1.getLeftBound(), b1.getBottomBound()) ) )
		{
			return true;
		}
		
		if( getCollisionTriangleToPoint(b2, new Point(b1.getRightBound(), b1.getTopBound()) ) )
		{
			return true;
		}
		
		if( getCollisionTriangleToPoint(b2, new Point(b1.getRightBound(), b1.getBottomBound()) ) )
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if a point collides with a circle.
	 * ( Point must be inside or on the edge of the circle )
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionCircleToPoint(Circle b1, Point b2)
	{
		double dis = GameMath.sqrt( GameMath.sqr(b1.getX() - b2.x) + GameMath.sqr(b1.getY() - b2.y) );
		
		if( dis <= b1.getRadius() )
			return true;
		
		return false;
	}
	
	public static boolean getCollisionCircleToLine(Circle b1, Line b2)
	{
		
		if(getCollisionCircleToPoint(b1, new Point(b2.getStartX(), b2.getStartY())))
		{
			return true;
		}
		else if(getCollisionCircleToPoint(b1, new Point(b2.getEndX(), b2.getEndY())))
		{
			return true;
		}
		else
		{
			double startX = b1.getX();
			double startY = b1.getY();
			double endX = b1.getX();
			double endY = b1.getY();
			
			Vec2f p = new Vec2f((b2.getEndY()-b2.getStartY()), -(b2.getEndX()-b2.getStartX()));
			
			p = GameMath.normalize(p);
			
			endX += p.x*b1.getRadius();
			startX -= p.x*b1.getRadius();
		
			endY += p.y*b1.getRadius();
			startY -= p.y*b1.getRadius();
		
			Line l = new Line(startX, startY, endX, endY);
			
			if(getCollisionLineToLine(l,b2))
			{
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Determines if a Circle collides with a line.
	 * (Fix Later. Does not work in some situations.)
	 * (May have to use the equation of a circle and junk)
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionCircleToLine_OLD(Circle b1, Line b2)
	{
		//Scratch all of that equation of a circle junk.
		//Use some vector math.
		
		if(getCollisionCircleToPoint(b1, new Point(b2.getStartX(), b2.getStartY())))
		{
			return true;
		}
		else if(getCollisionCircleToPoint(b1, new Point(b2.getEndX(), b2.getEndY())))
		{
			return true;
		}
		else
		{
			if(b2.getSlope()==0)
			{
				if(b2.getMinX()<=b1.getX()+b1.getRadius() && b2.getMaxX()>=b1.getX()-b1.getRadius())
				{
					if(b2.getMinY()<=b1.getY() && b2.getMaxY()>=b1.getY())
					{
						return true;
					}
				}
				
				if(b2.getMinY()<=b1.getY()+b1.getRadius() && b2.getMaxY()>=b1.getY()-b1.getRadius())
				{
					if(b2.getMinX()<=b1.getX() && b2.getMaxX()>=b1.getX())
					{
						return true;
					}
				}
			}
			else
			{
				Vec2f toCircle = new Vec2f(b1.getX()-b2.getStartX(), b1.getY()-b2.getStartY());
				
				if(toCircle.getLength() < b2.getVector().getLength())
				{
					Vec2f normalizedLineVec = GameMath.normalize(b2.getVector());
					Vec2f scaledVec = GameMath.vectorMult(normalizedLineVec, toCircle.getLength());
					
					if(getCollisionCircleToPoint(b1, new Point(b2.getStartX()+scaledVec.x, b2.getStartY()+scaledVec.y)))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	/**
	 * Determines if two circles are colliding with each other.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionCircleToCircle(Circle b1, Circle b2)
	{
		double dis = GameMath.sqrt( GameMath.sqr( b1.getX() - b2.getX() ) + GameMath.sqr( b1.getY() - b2.getY() ) );
		double totalRad = b1.getRadius() + b2.getRadius();
		
		if(dis <= totalRad)
			return true;
		
		return false;
	}
	
	/**
	 * Determines if a line is colliding with a point.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionLineToPoint(Line b1, Point b2)
	{
		if(b1.getVertical())
		{
			if(b2.x >= b1.getStartX() && b2.x <= b1.getEndX())
			{
				if(b2.y >= b1.getStartY() && b2.y <= b1.getEndY())
				{
					return true;
				}
			}
		}
		else
		{
			double tempY = ( (b1.getSlope()*b2.x) + b1.getYInt() );
			
			if(tempY == b2.y)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if two line segments are colliding.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionLineToLine(Line b1, Line b2)
	{
		if(b1.getVertical() && b2.getVertical())
		{
			if(b1.getStartX() == b2.getStartX())
			{
				if(b1.getMinY()<=b2.getMaxY() && b1.getMaxY()>=b2.getMinY())
				{
					return true;
				}
			}
		}
		else if(b1.getVertical())
		{
			double xVal = b1.getStartX();
			double yVal = (b2.getSlope()*xVal) + b2.getYInt();
			
			if(yVal >= b1.getMinY() && yVal<= b1.getMaxY())
			{
				if(xVal >= b2.getMinX() && xVal <= b2.getMaxX())
				{
					if(yVal >= b2.getMinY() && yVal <= b2.getMaxY())
					{
						return true;
					}
				}
			}
			
		}
		else if(b2.getVertical())
		{
			double xVal = b2.getStartX();
			double yVal = (b1.getSlope()*xVal) + b1.getYInt();
			
			if(yVal >= b2.getMinY() && yVal<= b2.getMaxY())
			{
				if(xVal >= b1.getMinX() && xVal <= b1.getMaxX())
				{
					if(yVal >= b1.getMinY() && yVal <= b1.getMaxY())
					{
						return true;
					}
				}
			}
		}
		else
		{
			//m1x+b1=m2x+b2 
			//x = (b2-b1) / (m1-m2)
			double subSlope =  b1.getSlope() - b2.getSlope();
			
			if(subSlope!=0)
			{
				double xVal = (b2.getYInt() - b1.getYInt()) / subSlope;
				double yVal = (b1.getSlope()*xVal) + b1.getYInt();
				
				if(xVal >= b1.getMinX() && xVal <= b1.getMaxX())
				{
					if(yVal >= b1.getMinY() && yVal<= b1.getMaxY())
					{
						if(xVal >= b2.getMinX() && xVal <= b2.getMaxX())
						{
							if(yVal >= b2.getMinY() && yVal <= b2.getMaxY())
							{
								return true;
							}
						}
					}
				}
				
			}
			else
			{
				
				//Same line different location
				//find middle point
				//test if collided with both lines
				
				//if y intercept is different, they aren't colliding
				//if y intercept are the same, they may be colliding
				if(b1.getYInt() == b2.getYInt())
				{
					if(b2.getMaxX() >= b1.getMinX() && b2.getMinX() <= b1.getMaxX())
					{
						if(b2.getMaxY() >= b1.getMinY() && b2.getMinY() <= b1.getMaxY())
						{
							return true;
						}
					}
				}
				
			}
			
		}
		
		return false;
	}
	
	/**
	 * Determines if a Circle collides with a Triangle
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionTriangleToCircle(Triangle b1, Circle b2)
	{
		//Draw a line from the center of the circle to each point on
		//the triangle. check if any lines collide with the triangle.
		
		Vec2f inverseLine = new Vec2f(0,0);
		Line l = new Line(0,0,0,0);
		
		inverseLine = new Vec2f( b1.getX1()-b2.getX(), b1.getY1()-b2.getY() );
		inverseLine = GameMath.normalize( inverseLine );
		
		inverseLine = GameMath.vectorMult(inverseLine, b2.getRadius());
		
		l = new Line(b2.getX()-inverseLine.x, b2.getY()-inverseLine.y, b2.getX()+inverseLine.x, b2.getY()+inverseLine.y);
		
		if(getCollisionTriangleToLine(b1,l))
		{
			return true;
		}
		
		inverseLine = new Vec2f( b1.getX2()-b2.getX(), b1.getY2()-b2.getY() );
		inverseLine = GameMath.normalize( inverseLine );
		
		inverseLine = GameMath.vectorMult(inverseLine, b2.getRadius());
		
		l = new Line(b2.getX()-inverseLine.x, b2.getY()-inverseLine.y, b2.getX()+inverseLine.x, b2.getY()+inverseLine.y);
		
		if(getCollisionTriangleToLine(b1,l))
		{
			return true;
		}
		
		inverseLine = new Vec2f( b1.getX3()-b2.getX(), b1.getY3()-b2.getY() );
		inverseLine = GameMath.normalize( inverseLine );
		
		inverseLine = GameMath.vectorMult(inverseLine, b2.getRadius());
		
		l = new Line(b2.getX()-inverseLine.x, b2.getY()-inverseLine.y, b2.getX()+inverseLine.x, b2.getY()+inverseLine.y);
		
		if(getCollisionTriangleToLine(b1,l))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if a Line collides with a Triangle.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionTriangleToLine(Triangle b1, Line b2)
	{
		//Treat the different parts of the triangle as lines.
		//first determine if any point of the line is in the triangle
		//the treat every thing as lines.
		
		if(getCollisionTriangleToPoint(b1, new Point(b2.getStartX(), b2.getStartY())))
		{
			return true;
		}
		
		if(getCollisionTriangleToPoint(b1, new Point(b2.getEndX(), b2.getEndY())))
		{
			return true;
		}
		
		Line l = new Line(b1.getX1(), b1.getY1(), b1.getX2(), b1.getY2());
		l.updateCollisionHull();
		
		if(getCollisionLineToLine(l,b2))
		{
			return true;
		}
		
		l.setStartX(b1.getX2());
		l.setStartY(b1.getY2());
		l.setEndX(b1.getX3());
		l.setEndY(b1.getY3());
		l.updateCollisionHull();
		
		if(getCollisionLineToLine(l,b2))
		{
			return true;
		}
		
		l.setStartX(b1.getX1());
		l.setStartY(b1.getY1());
		l.setEndX(b1.getX3());
		l.setEndY(b1.getY3());
		l.updateCollisionHull();
		
		if(getCollisionLineToLine(l,b2))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if a Triangle is colliding with a Point.
	 * That is to say that the point is on the edge or inside the triangle.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionTriangleToPoint(Triangle b1, Point b2)
	{
		//Trig version
		/*
		Vec2f vec1 = new Vec2f(b2.x - b1.getX1(), b2.y-b1.getY1());
		Vec2f vec2 = new Vec2f(b2.x - b1.getX2(), b2.y-b1.getY2());
		Vec2f vec3 = new Vec2f(b2.x - b1.getX3(), b2.y-b1.getY3());
		
		double angle = 0;
		double length1 = vec1.getLength();
		double length2 = vec2.getLength();
		double length3 = vec3.getLength();
		
		if(length1*length2 != 0)
			angle+= GameMath.arccos( GameMath.dot(vec1,vec2)/(length1*length2) );
			
		if(length2*length3 != 0)
			angle+= GameMath.arccos( GameMath.dot(vec2,vec3)/(length2*length3) );
			
		if(length3*length1 != 0)
			angle+= GameMath.arccos( GameMath.dot(vec3,vec1)/(length3*length1) );
		
		if(angle>=(2*GameMath.PI))
			return true;
		*/
		
		//No Trig version
		Vec2f toPoint = new Vec2f( b2.x - b1.getX1(), b2.y - b1.getY1());
		Vec2f toA = new Vec2f( b1.getX2() - b1.getX1(), b1.getY2() - b1.getY1());
		Vec2f toB = new Vec2f( b1.getX3() - b1.getX1(), b1.getY3() - b1.getY1());
		
		double dot1 = GameMath.dot(toB, toB);
		double dot2 = GameMath.dot(toB, toA);
		double dot3 = GameMath.dot(toB, toPoint);
		double dot4 = GameMath.dot(toA, toA);
		double dot5 = GameMath.dot(toA, toPoint);
		
		double divValue = 1/(dot1*dot4 - dot2*dot2);
		double u = (dot4*dot3 - dot2*dot5)*divValue;
		double v = (dot1*dot5 - dot2*dot3)*divValue;
		
		if( u>=0 && v>=0 && u+v<=1 )
			return true;
		
		return false;
	}
	
	public static boolean getCollisionTriangleToTriangle(Triangle b1, Triangle b2)
	{
		Line l = new Line(b2.getX1(), b2.getY1(), b2.getX2(), b2.getY2());
		if(getCollisionTriangleToLine(b1, l))
		{
			return true;
		}
		
		l = new Line(b2.getX1(), b2.getY1(), b2.getX3(), b2.getY3());
		if(getCollisionTriangleToLine(b1, l))
		{
			return true;
		}
		
		l = new Line(b2.getX2(), b2.getY2(), b2.getX3(), b2.getY3());
		if(getCollisionTriangleToLine(b1, l))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if two points are colliding.
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean getCollisionPointToPoint(Point b1, Point b2)
	{
		if(b1.x == b2.x)
			if(b1.y == b2.y)
				return true;
		
		return false;
	}
}
