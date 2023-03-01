#version 400 core

in vec2 passTexCoord;
in float test;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
    fragColor = vec4(cos((test + 0.5) * 50), 0, 0, 1);//texture(textureSampler, passTexCoord);
}