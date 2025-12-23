package com.example.security.tools;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

public class SvgToPngConverter {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: SvgToPngConverter <svg-file> [<output-png>]");
            System.exit(0);
        }
        Path svg = Path.of(args[0]);
        Path out;
        if (args.length >= 2) out = Path.of(args[1]);
        else out = svg.getParent().resolve(svg.getFileName().toString().replaceAll("\\.svg$", ".png"));

        System.out.println("Converting: " + svg.toAbsolutePath() + " -> " + out.toAbsolutePath());
        convert(svg.toFile(), out.toFile());
        System.out.println("Wrote: " + out.toAbsolutePath());
    }

    public static void convert(File svgFile, File pngFile) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(pngFile)) {
            String uri = svgFile.toURI().toString();
            System.out.println("SVG URI: " + uri);
            TranscoderInput input = new TranscoderInput(uri);
            TranscoderOutput output = new TranscoderOutput(fos);
            PNGTranscoder t = new PNGTranscoder();
            t.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, java.awt.Color.WHITE);
            t.transcode(input, output);
            fos.flush();
        }
    }
}
