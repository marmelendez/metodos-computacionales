//Problema 1
(define (fahrenheit-to-celsius f)
    (if (number? f)
        (/ (* 5 (- f 32)) 9)
        'not_number
    )
)

//Problema 2
(define (sign n)
    (cond
        ((not (integer? n)) 'not_integer)
        ((> n 0) 1)
        ((< n 0) -1)
        (else 0)
    )
)

//Problema 3

//Problema 4

//Problema 6
(define (duplicate lista)
    (cond 
        ((null? lista) '())
        (else (cons (car lista) (cons (car lista) (duplicate (cdr lista)))))
    )
)

//Problema 10
(define (positives lista)
    (cond 
        ((null? lista) '())
        (else 
            (if (>= (car lista) 0)
                (cons (car lista) (positives (cdr lista)))
                (positives (cdr lista))
            )
        )
    )
)
