
varying vec2 v_vTexcoords;

uniform sampler2D texture;
uniform vec4 color;
uniform float powValue;

void main()
{
	vec3 col = texture2D(texture,v_vTexcoords).rgb;
	
	col.r = pow(col.r, powValue);
	col.g = pow(col.g, powValue);
	col.b = pow(col.b, powValue);

	gl_FragColor = vec4(col,1.0) * color;
}