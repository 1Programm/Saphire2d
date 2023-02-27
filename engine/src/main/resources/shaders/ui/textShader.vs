#version 400 core
in vec2 pos;
in vec2 texCoord;

out vec2 passTexCoord;

uniform mat4 projection;
uniform mat4 transform;

uniform vec2 tex_offset;
uniform vec2 tex_scale;

void main()
{
    gl_Position = projection * transform * vec4(pos, 0.0, 1.0);
    passTexCoord = texCoord * tex_scale + tex_offset;
}