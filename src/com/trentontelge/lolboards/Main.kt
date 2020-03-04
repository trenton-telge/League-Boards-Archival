package com.trentontelge.lolboards

import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.util.*


fun main() {
    var pageCurrent = 0
    val tlIndex:Vector<String> = Vector()
    var profileIndex:Vector<String>
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
                FileOutputStream(File(outputParent.toString() + System.getProperty("file.separator") + "p" + pageCurrent+1 + ".html")).use({ fileOutputStream ->
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
    //TODO
    println("Finished Indexing Pofiles")
    println("Indexing Posts")
    //TODO
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