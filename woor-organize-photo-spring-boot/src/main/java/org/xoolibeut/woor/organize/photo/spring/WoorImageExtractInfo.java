package org.xoolibeut.woor.organize.photo.spring;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.springframework.stereotype.Component;

@Component
public class WoorImageExtractInfo {

	public TagInfoPhoto readAndDisplayMetadata(final File file) throws ImageReadException, IOException {
		// get all metadata stored in EXIF format (ie. from JPEG or TIFF).
		TagInfoPhoto tagInfoPhoto = new TagInfoPhoto();
		tagInfoPhoto.setPath(file.getAbsolutePath());
		IImageMetadata metadata = Sanselan.getMetadata(file);
		if (metadata instanceof JpegImageMetadata) {
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			String datePrise = getTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
			SimpleDateFormat formatter =new SimpleDateFormat("''yyyy:MM:dd HH:mm:ss''");
			try {
				tagInfoPhoto.setDatePrise(formatter.parse(datePrise));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			tagInfoPhoto.setMarqueAppareil(getTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_MAKE));
			tagInfoPhoto.setModel(getTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_MODEL));
			// simple interface to GPS data
			TiffImageMetadata exifMetadata = jpegMetadata.getExif();
			if (null != exifMetadata) {
				TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
				if (null != gpsInfo) {
					tagInfoPhoto.setLatitude(gpsInfo.getLatitudeAsDegreesNorth());
					tagInfoPhoto.setLongitude(gpsInfo.getLongitudeAsDegreesEast());
				}
			}
		}

		return tagInfoPhoto;
	}

	private static String getTagValue(final JpegImageMetadata jpegMetadata, final TagInfo tagInfo) {
		TiffField field = jpegMetadata.findEXIFValue(tagInfo);
		if (field == null) {
			return null;
		}
		return field.getValueDescription();
	}
}
