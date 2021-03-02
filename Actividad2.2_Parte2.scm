//Problema 1


//Problema 3


//Problema 6
(define (deep-reverse lista)
    (if (not (list? lista)) 
        'not_list
        (recursive-reverse lista)
    )
)

(define (recursive-reverse lista)
    (cond
        ((not (list? lista)) lista)
        (else (reverse (map recursive-reverse lista)))
    )
)