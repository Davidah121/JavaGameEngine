
varying vec2 v_vTexcoords;

uniform vec2 pixel;
uniform int size;

uniform sampler2D screenTexture;

uniform vec4 color;

vec4 antiAliasing(int radius)
{
	vec4 totalColor = vec4(0.0,0.0,0.0,0.0);
	float divValue = radius*radius;
	
	vec2 temp = vec2(0.0,0.0);
	
	for(int x=0; x<radius; x++)
	{
		for(int y=0; y<radius; y++)
		{
			temp.x = pixel.x * x;
			temp.y = pixel.y * y;
			
			totalColor += texture2D( screenTexture, v_vTexcoords + temp );
		}
	}
	
	return totalColor/divValue;
}


void main()
{
	gl_FragColor = antiAliasing(size) * color;
}