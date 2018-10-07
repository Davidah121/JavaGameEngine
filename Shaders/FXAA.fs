
varying vec2 v_vTexcoords;

uniform vec2 pixel;
uniform int size;

uniform sampler2D screenTexture;


float getBrightness(vec4 color)
{
	return length(color);
}

vec4 antiAliasing(float size)
{
	vec4 baseColor = texture2D(screenTexture,v_vTexcoords);
	int i = 0;
	
	while(i<size)
	{
		baseColor += texture2D(screenTexture,v_vTexcoords+ (pixel*i));
		baseColor += texture2D(screenTexture,v_vTexcoords+ (pixel*-i));
		baseColor += texture2D(screenTexture,v_vTexcoords+ vec2(pixel.x*-i, pixel.y*i));
		baseColor += texture2D(screenTexture,v_vTexcoords+ vec2(pixel.x*i, pixel.y*-i));
		i+=1;
	}
	vec4 finalColor = baseColor / ((i*4)+1);
	
	return finalColor;
}


vec4 blurColor()
{
	vec4 baseColor = texture2D(screenTexture,v_vTexcoords);
	baseColor += texture2D(screenTexture,v_vTexcoords+ 0.001);
	baseColor += texture2D(screenTexture,v_vTexcoords- 0.001);
	
	return baseColor / 3.0;
}

void main()
{
	gl_FragColor = blurColor();
}