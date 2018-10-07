
varying vec2 pos;

void main()
{
	vec4 p1 = vec4(0.0, 0.0, 0.0, 1.0);
	vec4 p2 = vec4(1.0, 0.0, 0.0, 1.0);
	vec4 p3 = vec4(0.0, 1.0, 0.0, 1.0);
	vec4 p4 = vec4(0.0, 0.0, 1.0, 1.0);
	
	vec2 blendFactor = pos/128.0;
	
	vec4 topColor = mix(p1, p2, blendFactor.x);
	
	vec4 bottomColor = mix(p3, p4, blendFactor.x);
	
	vec4 finalColor = mix(topColor, bottomColor, blendFactor.y);
	
	gl_FragColor = finalColor;
}