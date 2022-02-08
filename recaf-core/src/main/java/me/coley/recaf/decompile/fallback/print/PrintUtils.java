package me.coley.recaf.decompile.fallback.print;

import me.coley.cafedude.ConstPool;
import me.coley.cafedude.annotation.*;
import me.coley.cafedude.constant.*;
import me.coley.recaf.util.StringUtil;
import org.objectweb.asm.Type;

import java.util.stream.Collectors;

/**
 * Various printing utilities.
 *
 * @author Matt Coley
 */
public class PrintUtils {
	/**
	 * @param pool
	 * 		Pool to pull data from.
	 * @param annotation
	 * 		Annotation to represent.
	 *
	 * @return String display of the annotation.
	 */
	public static String annotationToString(ConstPool pool, Annotation annotation) {
		String annotationDesc = pool.getUtf(annotation.getTypeIndex());
		String args = annotation.getValues().entrySet().stream()
				.map(e -> pool.getUtf(e.getKey()) + " = " + elementToString(pool, e.getValue()))
				.collect(Collectors.joining(", "));
		String annotationName = Type.getType(annotationDesc).getInternalName();
		return "@" + StringUtil.shortenPath(annotationName) + "(" + args + ")";
	}

	/**
	 * @param pool
	 * 		Pool to pull data from.
	 * @param elementValue
	 * 		Annotaiton element to represent.
	 *
	 * @return String display of the element.
	 */
	public static String elementToString(ConstPool pool, ElementValue elementValue) {
		char tag = elementValue.getTag();
		switch (tag) {
			case '@': // Annotation
			{
				AnnotationElementValue annotationElementValue = (AnnotationElementValue) elementValue;
				Annotation annotation = annotationElementValue.getAnnotation();
				return annotationToString(pool, annotation);
			}
			case '[': // Array of values
			{
				ArrayElementValue arrayElementValue = (ArrayElementValue) elementValue;
				String elements = arrayElementValue.getArray().stream()
						.map(element -> elementToString(pool, element))
						.collect(Collectors.joining(", "));
				return "{ " + elements + " }";
			}
			case 'c': // Class
			{
				ClassElementValue classElementValue = (ClassElementValue) elementValue;
				CpClass cpClass = (CpClass) pool.get(classElementValue.getClassIndex());
				String className = pool.getUtf(cpClass.getIndex());
				return StringUtil.shortenPath(className) + ".class";
			}
			case 'e': // Enum
			{
				EnumElementValue enumElementValue = (EnumElementValue) elementValue;
				CpClass cpClass = (CpClass) pool.get(enumElementValue.getTypeIndex());
				String enumName = pool.getUtf(cpClass.getIndex());
				String enumEntryName = pool.getUtf(enumElementValue.getNameIndex());
				return StringUtil.shortenPath(enumName) + "." + enumEntryName;
			}
			case 's': // String
			{
				Utf8ElementValue utf8ElementValue = (Utf8ElementValue) elementValue;
				return "\"" + pool.getUtf(utf8ElementValue.getUtfIndex()) + "\"";
			}
			case 'Z': // boolean
			case 'B': // byte
			case 'C': // char
			case 'S': // short
			case 'I': // int
			{
				PrimitiveElementValue primitiveElementValue = (PrimitiveElementValue) elementValue;
				CpInt cpInt = (CpInt) pool.get(primitiveElementValue.getValueIndex());
				return String.valueOf(cpInt.getValue());
			}
			case 'J': // long
			{
				PrimitiveElementValue primitiveElementValue = (PrimitiveElementValue) elementValue;
				CpLong cpLong = (CpLong) pool.get(primitiveElementValue.getValueIndex());
				return String.valueOf(cpLong.getValue());
			}
			case 'D': // double
			{
				PrimitiveElementValue primitiveElementValue = (PrimitiveElementValue) elementValue;
				CpDouble cpDouble = (CpDouble) pool.get(primitiveElementValue.getValueIndex());
				return String.valueOf(cpDouble.getValue());
			}
			case 'F': // float
			{
				PrimitiveElementValue primitiveElementValue = (PrimitiveElementValue) elementValue;
				CpFloat cpFloat = (CpFloat) pool.get(primitiveElementValue.getValueIndex());
				return String.valueOf(cpFloat.getValue());
			}
			default:
				// Unknown / unsupported
				break;
		}
		return "<unknown-value-type:" + tag + ">";
	}
}
