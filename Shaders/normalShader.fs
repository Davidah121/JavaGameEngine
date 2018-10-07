varying vec2 v_vTexcoords;
varying vec3 v_vNormals;

uniform sampler2D texture;
uniform vec4 color;

void main()
{
	gl_FragColor = vec4(v_vTexcoords,0.0,1.0);
	//gl_FragColor = vec4(v_vNormals,1.0);
}