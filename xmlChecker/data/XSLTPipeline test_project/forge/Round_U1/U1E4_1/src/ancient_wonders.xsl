<?xml version="1.0"  encoding="utf-8"?>
<!-- U1E3: Students' version of ancient_wonder.xsl. Requires filling with correct XPath statements. -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="text"/>

	<xsl:template match="/" mode="kysymys11">
		<xsl:text>Kysymys 11 &#xA;</xsl:text>
		<!-- Update v2 2015-01-19 -->
		<xsl:text>kaikkien kuvaelementtien (main_image) kaikkien attribuuttien valinta:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS11">
			<xsl:value-of select="."/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys12">
		<xsl:text>Kysymys 12 &#xA;</xsl:text>
		<xsl:text>kaikkien ihmeiden (wonder) nimi-elementit, jotka ovat kreikaksi:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS12">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys13">
		<xsl:text>Kysymys 13 &#xA;</xsl:text>
		<xsl:text>kaikkien niiden ihmeiden ”wonder”-elementit, jotka on tuhottu (year_destroyed) ennen ajanlaskua (BC):&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS13">
			<xsl:value-of select="@id"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys14">
		<xsl:text>Kysymys 14 &#xA;</xsl:text>
		<xsl:text>kaikkien niiden ihmeiden nimielementit, jotka ovat tuhoutuneet vuosien 1300AD ja 1500AD välillä:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS14">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<!-- HUOM! Tästä alaspäin kysymykset ovat funktiokysymyksiä -->
	
	<xsl:template match="/" mode="kysymys15">
		<xsl:text>Kysymys 15 &#xA;</xsl:text>
		<xsl:text>kaikkien niiden ihmeiden nimielementit, joiden korkeus on yli 100m (huom: metriä)?:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS15">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys16">
		<xsl:text>Kysymys 16 &#xA;</xsl:text>
		<xsl:text>Kaikkien ”source”-elementtien attribuuttien lukumäärä:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS16"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	
	<xsl:template match="/" mode="kysymys17">
		<xsl:text>Kysymys 17 &#xA;</xsl:text>
		<xsl:text>Kaikkien niiden ihmeiden yhteiskorkeus (summa) jaloissa (feet), joiden pituus on ilmaistu jalossa (feet):&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS17"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys18">
		<xsl:text>Kysymys 18 &#xA;</xsl:text>
		<xsl:text>Sisältääkö neljännen ihmeen nimi sanan ’Lighthouse’:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS18"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys19">
		<xsl:text>Kysymys 19 &#xA;</xsl:text>
		<xsl:text>Laske viimeisen kuvan (main_image) pikselien määrä?:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS19"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys20">
		<xsl:text>Kysymys 20 &#xA;</xsl:text>
		<xsl:text>Missä kaupungissa (vain kaupunki, ei valtiota) sijaitsee 'Mausoleum at Halicarnassus' niminen maailman ihme?:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS20"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	

	<!-- Älä muokkaa alla olevaa osiota tiedostosta mitenkään! -->

	<xsl:param name="kysymys">all</xsl:param>

	<xsl:template match="/">
		<xsl:text>&#xA;</xsl:text>
		<xsl:choose>
			<xsl:when test="$kysymys='11'">
				<xsl:apply-templates select="." mode="kysymys11"/>
			</xsl:when>
			<xsl:when test="$kysymys='12'">
				<xsl:apply-templates select="." mode="kysymys12"/>
			</xsl:when>
			<xsl:when test="$kysymys='13'">
				<xsl:apply-templates select="." mode="kysymys13"/>
			</xsl:when>
			<xsl:when test="$kysymys='14'">
				<xsl:apply-templates select="." mode="kysymys14"/>
			</xsl:when>
			<xsl:when test="$kysymys='15'">
				<xsl:apply-templates select="." mode="kysymys15"/>
			</xsl:when>
			<xsl:when test="$kysymys='16'">
				<xsl:apply-templates select="." mode="kysymys16"/>
			</xsl:when>
			<xsl:when test="$kysymys='17'">
				<xsl:apply-templates select="." mode="kysymys17"/>
			</xsl:when>
			<xsl:when test="$kysymys='18'">
				<xsl:apply-templates select="." mode="kysymys18"/>
			</xsl:when>
			<xsl:when test="$kysymys='19'">
				<xsl:apply-templates select="." mode="kysymys19"/>
			</xsl:when>
			<xsl:when test="$kysymys='20'">
				<xsl:apply-templates select="." mode="kysymys20"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="kysymys11"/>
				<xsl:apply-templates select="." mode="kysymys12"/>
				<xsl:apply-templates select="." mode="kysymys13"/>
				<xsl:apply-templates select="." mode="kysymys14"/>
				<xsl:apply-templates select="." mode="kysymys15"/>
				<xsl:apply-templates select="." mode="kysymys16"/>
				<xsl:apply-templates select="." mode="kysymys17"/>
				<xsl:apply-templates select="." mode="kysymys18"/>
				<xsl:apply-templates select="." mode="kysymys19"/>
				<xsl:apply-templates select="." mode="kysymys20"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text>***</xsl:text>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

</xsl:stylesheet>
