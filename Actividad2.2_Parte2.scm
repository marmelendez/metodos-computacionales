//Problema 1


//Problema 3


//Problema 6
(define (deep-reverse l)
    (if (null? l)
        '()
        (if (list? (car l))
            (append (deep-reverse (cdr l)) (list (deep-reverse (car l))))
            (append (deep-reverse (cdr l)) (list (car l)))
        )
    )
)