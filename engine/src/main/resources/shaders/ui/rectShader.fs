#version 400 core

out vec4 fragColor;

uniform vec4 color;

void main()
{
    fragColor = color;
}