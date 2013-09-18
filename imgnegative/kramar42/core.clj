(ns lab1.core
  (:gen-class))

(import '(javax.imageio ImageIO)
        '(java.io File)
        '(java.awt Rectangle)
        '(java.awt.image SampleModel))

(def THREADS_NO 10)
(def imgPath
  "/Users/kramar/Downloads/nMBA77wxlwE.jpg")

(defn makeNegFilename
  "Adds _neg to filename before file extension"
  [filename]
  (let [dot (.lastIndexOf filename ".")]
    (str (.substring filename 0 dot) "_neg"
         (.substring filename dot))))

(defn BImageFromFile
  "Returns java.awt.image.BufferedImage from file name"
  [filename]
  (. ImageIO read (File. filename)))

(defn BImageToFile
  "Write BufferedImage to file by name"
  [image filename]
  (. ImageIO write image "jpeg" (File. filename)))

(defn negativeImage
  "Returns BufferedImage that contains all negative pixels from original one"
  [image]
  (let [width (.getWidth image)
        height (.getHeight image)
        data (.getRGB image
                      0 0 width height
                      (int-array (* width height))
                      0 width)]
    (.setRGB image 0 0 width height
             (int-array (map #(- 255 %) data)) 0 width)
    image))

(defn sliceImage
  "Returns #count slices as arrays of ints from BufferedImage"
  [image count]
  (let [width (.getWidth image)
        height (.getHeight image)
        size (int (/ width count))
        bounds (range 0 width size)]
    
    ;(map #(.getRGB image %1 %2 %3 %4 nil 0 width)
    (map list
         (cons (last bounds) (butlast bounds))
         (repeat (+ count 1) 0)
         (cons (- width (last bounds))
               (repeat count size))
         (repeat (+ count 1) height))))

(defn reduceSlices
  "Returns image pasted from slices"
  [image slices]
  (let [width (.getWidth image)
        height (.getHeight image)
        count (count slices)
        size (/ width count)
        bounds (range 0 width size)]
    (map #(.setRGB image %1 %2 %3 %4 %5 0 width)
         (cons (last bounds) (butlast bounds))
         (repeat (+ count 1) 0)
         (cons (- width (last bounds))
               (repeat count size))
         (repeat (+ count 1) height)
         slices)
    image))
  
(defn -main
  "I don't do a whole lot ... yet."
  [imgPath count]
  (let [image (BImageFromFile imgPath)]
    (BImageToFile
     (reduceSlices image
                   (map (fn [array] (int-array (map (fn [elem]
                                          (- 255 elem))
                                        array)))
                        (sliceImage image count)))
     (makeNegFilename imgPath))))
