package nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TestDirectBuffer {
	public static void main(String[] args) {
		// 3 vertices (x, y, z) of the triangle
		float[] vertices = { 0.0f, 1.0f, 0.0f, // top (x, y, z)
				-1.0f, -1.0f, 0.0f, // left-bottom (x, y, z)
				1.0f, -1.0f, 0.0f // right-bottom (x, y, z)
		};

		FloatBuffer vertexBuffer;

		// Setup vertex-array buffer. Vertices in float.

		// Allocate a direct ByteBuffer for the vertices. A float has 4 bytes.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		// Set the byte order (big-endian or little-endian) to the native
		// byte order of the underlying platform for portable program.
		vbb.order(ByteOrder.nativeOrder());
		// Create a direct FloatBuffer as a view of this ByteBuffer.
		// Position is 0.
		vertexBuffer = vbb.asFloatBuffer();
		// Copy the data from float[] to the buffer from the current position.
		vertexBuffer.put(vertices);
		// Rewind by setting position to 0.
		vertexBuffer.position(0);

		// OpenGL can use the FloatBuffer directly
		// gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	}
}
