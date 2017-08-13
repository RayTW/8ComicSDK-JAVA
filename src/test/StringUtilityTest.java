package test;

import static org.junit.Assert.*;
import net.xuite.blog.ray00000test.library.util.StringUtility;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void testSubstring() {
		String result = StringUtility.substring("aabbccdd", "aa", "d");
		System.out.println(result);
		assertEquals("bbcc", result);
	}

}
