package com.trentontelge.lolboards

import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


@Suppress("DuplicatedCode")
fun main() {
    var pageCurrent = 0
    val tlIndex:Vector<String> = Vector()
    val profileIndex:Vector<String> = Vector()
    val postIndex:Vector<String> = Vector()
    val tlPrefix = "https://boards.na.leagueoflegends.com/en/?sort_type=recent&num_loaded="
    val outputParent = File(System.getProperty("user.home") + System.getProperty("file.separator") + "boards.na.leagueoflegends.com" + System.getProperty("file.separator"))
    outputParent.mkdir()
    println("Backing Up boards.na.leagueoflegends.com to " + outputParent.path.toString())
    println("Indexing Boards")
    while(pageCurrent < 10000){
        tlIndex.addElement(tlPrefix + 50*pageCurrent)
        if (pageCurrent > 0 && pageCurrent%100 == 0){
            println(((pageCurrent/100)).toString() + " percent complete.")
        }
        pageCurrent++
    }
    println("Finished Indexing")
    println("Backing Up Top Level")
    pageCurrent = 0
    while (pageCurrent < 10000){
        try {
            BufferedInputStream(URL(tlIndex[pageCurrent]).openStream()).use { `in` ->
                FileOutputStream(File(outputParent.toString() + System.getProperty("file.separator") + "p" + (pageCurrent+1L) + ".html")).use({ fileOutputStream ->
                    val dataBuffer = ByteArray(1024)
                    var bytesRead: Int
                    while (`in`.read(dataBuffer, 0, 1024).also { bytesRead = it } != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead)
                    }
                })
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (pageCurrent > 0 && pageCurrent%10 == 0){
            println(((pageCurrent/100F)).toString() + " percent complete.")
        }
        pageCurrent++
    }
    println("Finished Top Level")
    println("Indexing Users")
    pageCurrent = 0
    val unfilteredUserResults:Vector<String> = Vector()
    while (pageCurrent < 10000){
        val p = Pattern.compile("<span class=\"username\">(.*)</span>")
        val matcher = p.matcher("") // Create a matcher for the pattern
        Files.lines(File(outputParent.toString() + System.getProperty("file.separator") + "p" + pageCurrent+1 + ".html").toPath())
                .map { input: String? -> matcher.reset(input) } // Reuse the matcher object
                .filter { obj: Matcher -> obj.matches() }
                .findFirst()
                .ifPresent { m: Matcher -> unfilteredUserResults.addElement(m.group(1)) }
        if (pageCurrent > 0 && pageCurrent%10 == 0){
            println(((pageCurrent/100F)).toString() + " percent complete.")
        }
        pageCurrent++
    }
    println("    Converting To Links")
    for (i in unfilteredUserResults){
        if (!profileIndex.contains("https://boards.na.leagueoflegends.com/en/player/NA/" + i.substring(23, i.length-7))) {
            profileIndex.addElement("https://boards.na.leagueoflegends.com/en/player/NA/" + i.substring(23, i.length - 7))
        }
    }
    println("Finished Indexing Pofiles")
    println("Indexing Posts")
    //TODO
    pageCurrent = 0
    val unfilteredPostResults:Vector<String> = Vector()
    while (pageCurrent < 10000){
        val p = Pattern.compile("href=\"/en/c/(\\w+-)*(\\w+)/(\\w+-)*(\\w+)\"")
        val matcher = p.matcher("") // Create a matcher for the pattern
        Files.lines(File(outputParent.toString() + System.getProperty("file.separator") + "p" + pageCurrent+1 + ".html").toPath())
                .map { input: String? -> matcher.reset(input) } // Reuse the matcher object
                .filter { obj: Matcher -> obj.matches() }
                .findFirst()
                .ifPresent { m: Matcher -> unfilteredPostResults.addElement(m.group(1)) }
        if (pageCurrent > 0 && pageCurrent%10 == 0){
            println(((pageCurrent/100F)).toString() + " percent complete.")
        }
        pageCurrent++
    }
    println("    Converting To Links")
    for (i in unfilteredPostResults){
        if(!postIndex.contains("https://boards.na.leagueoflegends.com/$i")){
            postIndex.addElement("https://boards.na.leagueoflegends.com/$i")
        }
    }
    println("Finished Indexing Posts")
    println("Indexing Assets")
    //TODO
    println("Finished Indexing Assets")
    println("Backing Up Profiles")
    //TODO
    println("Finished Backing Up Profiles")
    println("Backing Up Posts")
    //TODO
    println("Finished Backing Up Posts")
    println("Backing Up Assets")
    //TODO
    println("Finished Backing Up Assets")
    println("Performing Link Rewrite")
    //TODO
    println("Link Rewrite Complete")
    println("Full Board Backup Complete. GLHF.")
}