#version 400 core

in float test;

out vec4 fragColor;

uniform vec4 color;

void main()
{
    fragColor = color;//vec4(test + 0.5, 0, 0, 1);
}